package per.search.json.web;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;
import per.search.action.ProductList;
import per.search.json.util.JSON_UTIL;
import per.search.model.Product;

@Log4j
public class Controller extends AbstractJsonServlet {

	private static final long serialVersionUID = 1879685741605549102L;

	public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		log.info("Controller invoked");

		StringBuilder jsonStringOutput = new StringBuilder();
		String action = request.getParameter("action");
		System.out.println("action="+action);
		Map<String, String[]> paramMap = request.getParameterMap();
		for (String key : paramMap.keySet()) {
			System.out.println("key="+key);
			System.out.print("  ");
			for (String v : paramMap.get(key)) {
				System.out.print(v+" ");
			}
			System.out.println();
		}
		
		if (action == "product_list") {
			Collection<Product> list = new ProductList().process(paramMap);
			jsonStringOutput.append(JSON_UTIL.convert(list));
		} else {
			jsonStringOutput.append(JSON_UTIL.convert("empty"));
		}
		
		jsonOutput(response, jsonStringOutput.toString());
	}
}