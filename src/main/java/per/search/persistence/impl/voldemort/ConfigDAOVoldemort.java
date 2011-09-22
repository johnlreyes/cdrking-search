package per.search.persistence.impl.voldemort;

import per.search.persistence.ConfigDAO;
import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;

public class ConfigDAOVoldemort implements ConfigDAO {

	private static StoreClient<String, String> client = null;

	private String bootStrapUrl;

	@Override
	public boolean put(String key, String value) {
		try {
			if (client == null) {
				client = getVoldemortClient();
			}
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
			if (client == null) {
				client = getVoldemortClient();
			}
			returnValue = (String) client.getValue(key);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnValue;
	}

	public void setBootStrapUrl(String bootStrapUrl) {
		this.bootStrapUrl = bootStrapUrl;
	}

	private StoreClient<String, String> getVoldemortClient() throws Exception {
		StoreClientFactory factory = new SocketStoreClientFactory(new ClientConfig().setBootstrapUrls(bootStrapUrl));
		return factory.getStoreClient("config");
	}
}