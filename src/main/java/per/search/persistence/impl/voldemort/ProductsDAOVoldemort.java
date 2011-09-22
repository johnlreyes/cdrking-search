package per.search.persistence.impl.voldemort;

import java.util.ArrayList;
import java.util.Collection;

import lombok.extern.log4j.Log4j;
import per.search.model.Product;
import per.search.persistence.ProductsDAO;
import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;

@Log4j
public class ProductsDAOVoldemort implements ProductsDAO {

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
		return factory.getStoreClient("products");
	}

	@Override
	public boolean put(String key, Product product) {
		try {
			client.put(key, product.toJSON());
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public Product get(String key) {
		Product returnValue = null;
		try {
			returnValue = Product.toObject(client.getValue(key));
		} catch (Exception dontCare) {
		}
		return returnValue;
	}

	@Override
	public Collection<Product> getAll() {
		log.info("getAll");
		Collection<Product> returnValue = new ArrayList<Product>();
		int count = 0;
		int index = 14;
		String data = client.getValue("" + index);
		log.info("index=" + index + " count=" + count + "  data=" + data);
		while (data != null || (data == null && count < 10)) {
			if (data != null) {
				Product product = Product.toObject(data);
				if (product != null) {
					returnValue.add(product);
				}
			}
			index++;
			if (data == null) {
				count++;
			} else {
				count = 0;
			}
			data = client.getValue("" + index);
			log.info("index=" + index + " count=" + count + "  data=" + data);
		}
		return returnValue;
	}

}