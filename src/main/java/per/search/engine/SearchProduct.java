package per.search.engine;

import java.util.List;

import per.search.model.Product;

public interface SearchProduct {

	List<Product> search(String input);
}
