package per.search.json.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;
import per.search.thread.CdrKingSyncThread;

@Log4j
public class SynchronizeStopOngoing extends AbstractJsonServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		log.info("SynchronizeStopOngoing invoked");
		CdrKingSyncThread.stopOngoing = true;

		String output = "";
		jsonOutput(response, output);
	}
}