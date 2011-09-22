package per.search.model;

import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;

import lombok.Data;

import org.codehaus.jackson.map.ObjectMapper;

@Data
public class Product implements Serializable {

	private static final long serialVersionUID = 6334625128087459732L;
	
	private int sid;
	private String name;
	private String code;
	private String category;
	private String price;
	private String status;
	private long updated;

	public String toJSON() {
		String returnValue = "{}";
		try {
			ObjectMapper mapper = new ObjectMapper();
			Writer strWriter = new StringWriter();
			mapper.writeValue(strWriter, this);
			returnValue = strWriter.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnValue;
	}

	public static Product toObject(String jsonString) {
		Product returnValue = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			returnValue = mapper.readValue(jsonString, Product.class);
		} catch (Exception dontCare) {
		}
		return returnValue;
	}
}