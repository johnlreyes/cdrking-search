package per.search.model;

import lombok.Data;

@Data
public class Images {

	private String code;
	private byte[] small;
	private byte[] large;
}