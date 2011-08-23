package per.search;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SearchServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Thread.sleep(5000);
        } catch (Exception dontCare) {
        }
        PrintWriter out = response.getWriter();
        out.println("SearchServlet Executed");
        out.flush();
        out.close();
    }

}