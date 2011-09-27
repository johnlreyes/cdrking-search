package per.search.persistence.model;

import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;

import lombok.Data;

import org.codehaus.jackson.map.ObjectMapper;

@Data
public class History implements Serializable {

	private static final long serialVersionUID = 862209497482231332L;
	
	private int Id;
	private long startDate;
	private int startSid;
	private long endDate;
	private int endSid;

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
	
	public static History toObject(String jsonString) {
		History returnValue = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			returnValue = mapper.readValue(jsonString, History.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnValue;
	}
}