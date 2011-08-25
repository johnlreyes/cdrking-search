package per.search.model;

import lombok.Data;

@Data
public class History {

    private int Id;
    private long startDate;
    private int startSid;
    private long endDate;
    private int endSid;
}
