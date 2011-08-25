package per.search.web.json;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;

import org.json.JSONObject;

import per.search.thread.CdrKingSyncThread;

@Log4j
public class SynchronizeStopOngoing extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        log.info("SynchronizeStopOngoing invoked");
        CdrKingSyncThread.stopOngoing = true;

        JSONObject jsonObject = new JSONObject();

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
    }
}