package com.Kodebutikken.wishlist.model;

import java.time.LocalDate;
import java.util.List;

public class Wishlist {
    private Long id;
    private String name;
    private LocalDate dueDate;
    private Visibility visibility;
    private Profile profile;
    private List<Product> products;

    public Wishlist(long id, String name, LocalDate dueDate, Visibility visibility, Profile profile) {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.visibility = visibility;
        this.profile = profile;
    }

    public Wishlist() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Visibility getVisibility() {
        return visibility;
    }
    public void setVisibility(Visibility visibility){
        this.visibility = visibility;
    }

    public Profile getProfile() {
        return profile;
    }
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
