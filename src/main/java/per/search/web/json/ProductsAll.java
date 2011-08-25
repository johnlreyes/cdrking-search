package per.search.web.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;

import org.json.JSONArray;
import org.json.JSONObject;

import per.search.model.Product;
import per.search.persistence.ProductsDAO;

@Log4j
public class ProductsAll extends HttpServlet {
	
	private static final long serialVersionUID = 1879685741605549102L;
	
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        log.info("ProductsAll invoked");

        Collection<Product> list = ProductsDAO.getAll();
        Collection<JSONObject> jsonList = new ArrayList<JSONObject>();
        for (Product product : list) {
            jsonList.add(new JSONObject(product));
        }
        JSONArray jsonArray = new JSONArray(jsonList);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonArray);
        out.flush();
    }
}