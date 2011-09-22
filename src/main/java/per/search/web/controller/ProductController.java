package per.search.web.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import per.search.model.Product;
import per.search.persistence.ProductsDAO;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

	@Autowired
	private ProductsDAO productsDAO;

	@RequestMapping(value = "all", method = RequestMethod.GET)
	@ResponseBody
	public Collection<Product> all() {
		return productsDAO.getAll();
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public Collection<Product> list() {
		return productsDAO.getAll();
	}
}