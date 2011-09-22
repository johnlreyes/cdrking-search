package per.search.persistence.impl.voldemort;

import per.search.persistence.ConfigDAO;
import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;

public class ConfigDAOVoldemort implements ConfigDAO {

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
		return factory.getStoreClient("config");
	}

	@Override
	public boolean put(String key, String value) {
		try {
			client.put(key, value);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public String get(String key) {
		String returnValue = null;
		try {
			returnValue = (String) client.getValue(key);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnValue;
	}
}