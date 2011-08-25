package per.search.persistence;

import java.util.ArrayList;
import java.util.Collection;

import lombok.extern.log4j.Log4j;

import org.json.JSONObject;

import per.search.model.Product;
import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;

@Log4j
public class ProductsDAO {

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

    public static Collection<Product> getAll() {
        log.info("getAll");
        Collection<Product> returnValue = new ArrayList<Product>();
        int count = 0;
        int index = 14;
        String data = client.getValue(""+index);
        log.info("index="+index+" count="+count+"  data="+data);
        while (data != null || (data==null&&count < 10)) {
            if (data != null) {
                Product product = parseProduct(data);
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
            data = client.getValue(""+index);
            log.info("index="+index+" count="+count+"  data="+data);
        }
        return returnValue;
    }

    public static Product parseProduct(String json) {
        Product product = null;
        try {
            product = new Product();
            JSONObject jsonObject = new JSONObject(json);
            int sid = jsonObject.getInt("sid");
            String code = jsonObject.getString("code");
            String name = jsonObject.getString("name");
            String price = jsonObject.getString("price");
            String status = jsonObject.getString("status");
            product.setSid(sid);
            product.setCode(code);
            product.setName(name);
            product.setPrice(price);
            product.setStatus(status);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return product;
    }
}