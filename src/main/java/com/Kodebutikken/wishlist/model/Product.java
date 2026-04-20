package com.Kodebutikken.wishlist.model;

public class Product {
    private long id;
    private String name;
    private float price;
    private String description;
    private String productUrl;


    public Product() {
    }

    public Product(long id, String name, float price, String description, String productUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.productUrl = productUrl;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
}
