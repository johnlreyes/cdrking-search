package per.search.engine;

import java.util.List;

import per.search.persistence.model.Product;

public interface SearchProduct {

	List<Product> search(String input);
}
