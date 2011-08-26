package per.search.json.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractJsonServlet extends HttpServlet {

	private static final long serialVersionUID = 7961979454479542581L;

	protected void jsonOutput(final HttpServletResponse response, String output) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(output);
		out.flush();
	}
}
