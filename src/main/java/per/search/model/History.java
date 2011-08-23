package per.search.model;

import lombok.Data;

import java.util.Date;

@Data
public class History {

    private int Id;
    private long startDate;
    private int startSid;
    private long endDate;
    private int endSid;
}
