package per.search.persistence.impl.voldemort;

import java.util.ArrayList;
import java.util.Collection;

import lombok.extern.log4j.Log4j;
import per.search.model.History;
import per.search.persistence.SynchronizeHistoryDAO;
import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;

@Log4j
public class SynchronizeHistoryDAOVoldemort implements SynchronizeHistoryDAO {

	private static StoreClient<String, String> client = null;

	static {
		try {
			System.err.println("Connecting to Server");
			client = getVoldemortClient();
			System.err.println("Client connected to Server");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static StoreClient<String, String> getVoldemortClient() throws Exception {
		String bootstrapUrl = "tcp://localhost:6666";
		StoreClientFactory factory = new SocketStoreClientFactory(new ClientConfig().setBootstrapUrls(bootstrapUrl));
		return factory.getStoreClient("synchronize_history");
	}

	@Override
	public boolean put(String key, History history) {
		try {
			log.info("<<<put>>> key=" + key + "  value=" + history);
			client.put(key, history.toJSON());
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public History get(String key) {
		History returnValue = null;
		try {
			returnValue = History.toObject(client.getValue(key));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnValue;
	}

	@Override
	public Collection<History> getAll() {
		log.info("getAll");
		Collection<History> returnValue = new ArrayList<History>();
		int index = 1;
		String data = client.getValue("" + index);
		log.info("index=" + index + " data=" + data);
		while (data != null) {
			History history = History.toObject(data);
			log.info("index=" + index + " history=" + history);
			if (history != null) {
				System.err.println("[" + index + "] history=" + history);
				returnValue.add(history);
			}
			index++;
			data = client.getValue("" + index);
			log.info("index=" + index + " data=" + data);
		}
		return returnValue;
	}
}