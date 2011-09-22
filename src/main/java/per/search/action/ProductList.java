package per.search.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import per.search.model.Product;

public class ProductList implements ActionInterface<Collection<Product>> {

	@Override
	public Collection<Product> process(Map paramMap) {
		Collection<Product> returnValue = new ArrayList<Product>();
		
		return returnValue;
	}
}
