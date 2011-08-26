package per.search.json.web;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;
import per.search.json.util.JSON_UTIL;
import per.search.model.Product;
import per.search.persistence.ProductsDAO;

@Log4j
public class ProductsAll extends AbstractJsonServlet {

	private static final long serialVersionUID = 1879685741605549102L;

	public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		log.info("ProductsAll invoked");

		Collection<Product> list = ProductsDAO.getAll();
		String output = JSON_UTIL.convert(list);
		jsonOutput(response, output);
	}
}