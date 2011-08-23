package per.search.model;

import lombok.Data;

public
@Data
class Product {

    private String code;
    private int sid;
    private String category;
    private String name;
    private String price;
    private String status;
    private Information information;
    private Support support;

}