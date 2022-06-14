package com.example.codeexpdeez;

public class Cart {
    private String key;
    private String id;
    private String name;
    private String price;
    private String colour;
    private String size;
    private String quantity;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setAllParam(String id, String name, String price, String colour, String size, String quantity, String key){
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setColour(colour);
        this.setSize(size);
        this.setQuantity(quantity);
        this.setKey(key);
    }

}
