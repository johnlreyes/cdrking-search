package per.search.web.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import per.search.model.History;
import per.search.persistence.SynchronizeHistoryDAO;

public class SynchronizeHistory extends HttpServlet {

	private static final long serialVersionUID = 7580346993611842977L;

	public void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {

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