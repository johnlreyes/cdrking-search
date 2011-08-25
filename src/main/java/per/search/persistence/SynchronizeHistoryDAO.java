package per.search.persistence;

import java.util.ArrayList;
import java.util.Collection;

import lombok.extern.log4j.Log4j;

import org.json.JSONObject;

import per.search.model.History;
import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;

@Log4j
public class SynchronizeHistoryDAO {

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

    public static boolean put(String key, String value) {
        try {
            log.info("<<<put>>> key="+key+"  value="+value);
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

    public static Collection<History> getAll() {
        log.info("getAll");
        Collection<History> returnValue = new ArrayList<History>();
        int index = 1;
        String data = client.getValue(""+index);
        log.info("index="+index+" data="+data);
        while (data != null) {
            History history = parseHistory(data);
            log.info("index="+index+" history="+history);
            if (history != null) {
                System.err.println("["+index+"] history="+history);
                returnValue.add(history);
            }
            index++;
            data = client.getValue(""+index);
            log.info("index="+index+" data="+data);
        }
        return returnValue;
    }

    private static History  parseHistory(String data) {
        History history = null;
        try {
            history = new History();
            JSONObject jsonObject = new JSONObject(data);
            int id = jsonObject.getInt("id");
            long startDate = jsonObject.getLong("startDate");
            int startSid = jsonObject.getInt("startSid");
            long endDate = jsonObject.getLong("endDate");
            int endSid = jsonObject.getInt("endSid");
            history.setId(id);
            history.setStartDate(startDate);
            history.setStartSid(startSid);
            history.setEndDate(endDate);
            history.setEndSid(endSid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return history;
    }
}