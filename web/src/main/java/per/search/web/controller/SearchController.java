package per.search.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import per.search.engine.SearchProduct;
import per.search.persistence.model.Product;

@Controller
@RequestMapping(value = "/search")
public class SearchController {

	@Autowired
	private SearchProduct searchProduct;
	
	@RequestMapping(value = "byKey", params="key")
	@ResponseBody
	public List<Product> byKey(String key) {		
		return searchProduct.search(key);
	}
}