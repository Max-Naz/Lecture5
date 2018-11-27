package com.qatestlab.model;

public class ProductData {
    private String name;
    private int qty;
    private float price;

    public ProductData(String name, float price, int qty) {
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }
}
