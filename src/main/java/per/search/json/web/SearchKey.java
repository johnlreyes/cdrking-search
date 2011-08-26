package per.search.json.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;
import per.search.engine.SearchProduct;
import per.search.engine.SearchProductSimple;
import per.search.json.util.JSON_UTIL;
import per.search.model.Product;

@Log4j
public class SearchKey extends AbstractJsonServlet {
	
	private static final long serialVersionUID = -6229609795859026741L;

	public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		log.info("SearchKey invoked");

		String key = request.getParameter("key");
		log.info("key=" + key);

		SearchProduct searchProduct = new SearchProductSimple();
		List<Product> productHits = searchProduct.search(key);

		String output = JSON_UTIL.convert(productHits);
		jsonOutput(response, output);
	}
}