package per.search.json.web;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;
import per.search.thread.IndexThread;

@Log4j
public class IndexAll extends AbstractJsonServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		log.info("IndexAll invoked");

		ScheduledExecutorService oneTime = Executors.newScheduledThreadPool(1);
		oneTime.schedule(new IndexThread(), 1, TimeUnit.SECONDS);

		String output = "";
		jsonOutput(response, output);
	}
}