package per.search.json.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import per.search.json.util.JSON_UTIL;
import per.search.thread.CdrKingSyncThread;

public class SynchronizeStatus extends AbstractJsonServlet {

	private static final long serialVersionUID = 1L;

	private final static String ONGOING = "ongoing";
	private final static String STANDBY = "standby";

	public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

		Map<String, String> statusMap = new HashMap<String, String>();
		statusMap.put("status", CdrKingSyncThread.invoked ? ONGOING : STANDBY);

		String output = JSON_UTIL.convert(statusMap);
		jsonOutput(response, output);
	}
}