package per.search.json.util;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;

import per.search.model.Product;

public final class JSON_UTIL {

	public static String convert(Product product) {
		return new JSONObject(product).toString();
	}
	
	public static String convert(Collection<?> list) {
		Collection<JSONObject> jsonList = new ArrayList<JSONObject>();
		for (Object o : list) {
			jsonList.add(new JSONObject(o));
		}
		return new JSONArray(jsonList).toString();
	}
	
	public static String convert(Object o) {
		return new JSONObject(o).toString();
	}
}
