package com.example.codeexpdeez;

import java.util.HashMap;

public class EmartClass {
    private Integer id;
    private Integer name;
    private String price;
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap getStock() {
        return stock;
    }

    public void setStock(HashMap stock) {
        this.stock = stock;
    }

    private HashMap stock;

}
