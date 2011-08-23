package per.search.web.json;

import org.json.JSONArray;
import org.json.JSONObject;
import per.search.model.History;
import per.search.persistence.SynchronizeHistoryDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

public class SynchronizeHistory extends HttpServlet {

    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

        Collection<History> list = SynchronizeHistoryDAO.getAll();
        Collection<JSONObject> jsonList = new ArrayList<JSONObject>();
        for (History history : list) {
            jsonList.add(new JSONObject(history));
        }
        JSONArray jsonArray = new JSONArray(jsonList);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonArray);
        out.flush();
    }
}