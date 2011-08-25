package per.search.thread;

import lombok.extern.log4j.Log4j;

import org.json.JSONObject;

import per.search.CdrKingJson;
import per.search.model.History;
import per.search.persistence.ConfigDAO;
import per.search.persistence.ProductsDAO;
import per.search.persistence.SynchronizeHistoryDAO;

@Log4j
public class CdrKingSyncThread implements Runnable {

	public static String status = "standby";
	public static boolean invoked = false;
	public static boolean stopOngoing = false;

	int lastSid = 13;
	int historyId = 0;

	boolean hasStarted = false;
	boolean hasEnded = false;
	History history = new History();

	{
		log.info("adding shutdown hook, just in case");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				if (hasStarted && !hasEnded) {
					cleanUp();
					status = "shutdown";
				}
			}
		});
	}

	@Override
	public void run() {
		synchronized (this) {
			invoked = true;
			stopOngoing = false;
			history.setStartDate(System.currentTimeMillis());
			status = "invoked";

			try {
				String str = ConfigDAO.get("last_history_id");
				if (str != null) {
					historyId = Integer.valueOf(str);
				}
				historyId++;
			} catch (Throwable ex) {
				log.error(ex);
				historyId = -1;
				cleanUp();
				hasEnded = true;

				return;
			}
			history.setId(historyId);

			try {
				String str = ConfigDAO.get("last_sid");
				if (str != null) {
					lastSid = Integer.valueOf(str);
				}
				lastSid++;
			} catch (Throwable ex) {
				log.error(ex);
				lastSid = -1;
				cleanUp();
				hasEnded = true;

				return;
			}
			history.setStartSid(lastSid);
			log.info("Start SID : " + lastSid);
			hasStarted = true;

			String product = CdrKingJson.toString(lastSid);
			while (product != null) {
				boolean putStatus = ProductsDAO.put("" + lastSid, product);
				log.info("putStatus - products " + putStatus);
				log.info("added " + product);
				if (stopOngoing) {
					break;
				}
				updateTracking();
				lastSid++;
				product = CdrKingJson.toString(lastSid);
			}

			cleanUp();
			hasEnded = true;
		}
	}

	private void cleanUp() {
		updateTracking();
		invoked = false;
		status = "standby";
		stopOngoing = false;
	}

	private void updateTracking() {
		log.info("updateTracking");
		ConfigDAO.put("last_history_id", "" + history.getId());
		if (lastSid < 0) {
			lastSid = history.getStartSid();
		}
		history.setEndSid(lastSid);
		history.setEndDate(System.currentTimeMillis());
		SynchronizeHistoryDAO.put("" + history.getId(),
				new JSONObject(history).toString());
		ConfigDAO.put("last_sid", "" + lastSid);
	}
}
