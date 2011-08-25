package per.search.web.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;

import org.json.JSONObject;

import per.search.thread.IndexThread;

@Log4j
public class IndexAll extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        log.info("IndexAll invoked");
        
        ScheduledExecutorService oneTime = Executors
				.newScheduledThreadPool(1);
		oneTime.schedule(new IndexThread(), 1, TimeUnit.SECONDS);
		
        JSONObject jsonObject = new JSONObject();

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
    }
}