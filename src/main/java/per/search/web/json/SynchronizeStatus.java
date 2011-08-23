package per.search.web.json;

import org.json.JSONObject;
import per.search.CdrKingSyncThread;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class SynchronizeStatus extends HttpServlet {

    private final static String ONGOING = "ongoing";
    private final static String STANDBY = "standby";

    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

        Map<String, String> statusMap = new HashMap<String, String>();
        statusMap.put("status", CdrKingSyncThread.invoked ? ONGOING : STANDBY);

        JSONObject jsonObject = new JSONObject(statusMap);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
    }
}