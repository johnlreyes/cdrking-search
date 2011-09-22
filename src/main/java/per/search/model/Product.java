package per.search.model;

import lombok.Data;

@Data
public class Product {

	private int sid;
	private String name;
	private String code;
	private String category;
	private String price;
	private String status;
	private long updated;
}