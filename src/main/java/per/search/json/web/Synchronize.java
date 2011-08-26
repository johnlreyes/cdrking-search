package per.search.json.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import per.search.json.util.JSON_UTIL;
import per.search.thread.CdrKingSyncThread;

public class Synchronize extends AbstractJsonServlet {

	private static final long serialVersionUID = 6787632235482556751L;

	public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

		if (!CdrKingSyncThread.invoked) {
			ScheduledExecutorService oneTime = Executors.newScheduledThreadPool(1);
			oneTime.schedule(new CdrKingSyncThread(), 1, TimeUnit.SECONDS);
		}

		Map<String, String> statusMap = new HashMap<String, String>();
		while (CdrKingSyncThread.status == "standby") {
		}
		statusMap.put("status", CdrKingSyncThread.status);

		String output = JSON_UTIL.convert(statusMap);
		jsonOutput(response, output);
	}
}