package per.search.persistence;

import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;

public class ConfigDAO {

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

	public static boolean put(String key, String value) {
		try {
			client.put(key, value);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public static String get(String key) {
		String returnValue = null;
		try {
			returnValue = (String) client.getValue(key);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnValue;
	}
}