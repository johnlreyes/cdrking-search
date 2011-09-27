package per.search.persistence.impl;

import java.util.ArrayList;
import java.util.Collection;

import lombok.extern.log4j.Log4j;
import per.search.persistence.ProductsDAO;
import per.search.persistence.model.Product;

@Log4j
public class ProductsDAOImpl extends BaseDAO implements ProductsDAO {

	@Override
	public boolean put(String key, Product product) {
		return super.put(key, product);
	}

	@Override
	public Product get(String key) {
		return (Product) super.get(key);
	}

	@Override
	public Collection<Product> getAll() {
		log.info("getAll");
		Collection<Product> returnValue = new ArrayList<Product>();
		int count = 0;
		int index = 14;
		Product product = get("" + index);
		log.info("index=" + index + " count=" + count + "  product=" + product);
		while (product != null || (product == null && count < 10)) {
			if (product != null) {
				returnValue.add(product);
			}
			index++;
			if (product == null) {
				count++;
			} else {
				count = 0;
			}
			product = get("" + index);
			log.info("index=" + index + " count=" + count + "  product=" + product);
		}
		return returnValue;
	}
}