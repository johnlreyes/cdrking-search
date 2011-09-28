package per.search.thread;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import per.search.persistence.ConfigDAO;
import per.search.persistence.ProductsDAO;
import per.search.persistence.SynchronizeHistoryDAO;
import per.search.persistence.model.History;
import per.search.persistence.model.Product;
import per.search.source.http.Scrapper;

@Log4j
@Component
public class CdrKingSyncThread implements Runnable {

	@Autowired
	private ConfigDAO configDAO;

	@Autowired
	private ProductsDAO productsDAO;

	@Autowired
	private SynchronizeHistoryDAO synchronizeHistoryDAO;

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
		invoked = true;
		stopOngoing = false;
		history.setStartDate(System.currentTimeMillis());
		status = "invoked";

		try {
			String str = configDAO.get("last_history_id");
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
			String str = configDAO.get("last_sid");
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

		Product product = Product.toObject(Scrapper.toString(lastSid));
		while (product != null) {
			boolean putStatus = productsDAO.put("" + lastSid, product);
			log.info("putStatus - products " + putStatus);
			log.info("added " + product);
			if (stopOngoing) {
				break;
			}
			updateTracking();
			lastSid++;
			product = Product.toObject(Scrapper.toString(lastSid));
		}

		cleanUp();
		hasEnded = true;
	}

	private void cleanUp() {
		updateTracking();
		invoked = false;
		status = "standby";
		stopOngoing = false;
	}

	private void updateTracking() {
		log.info("updateTracking");
		configDAO.put("last_history_id", "" + history.getId());
		if (lastSid < 0) {
			lastSid = history.getStartSid();
		}
		history.setEndSid(lastSid);
		history.setEndDate(System.currentTimeMillis());
		synchronizeHistoryDAO.put("" + history.getId(), history);
		configDAO.put("last_sid", "" + lastSid);
	}
}