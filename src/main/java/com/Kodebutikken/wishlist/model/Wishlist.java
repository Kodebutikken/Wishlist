package com.Kodebutikken.wishlist.model;

import java.time.LocalDate;
import java.util.List;

public class Wishlist {
    private long id;
    private LocalDate dueDate;
    private Visibility visibility;
    private Profile profile;
    private List<WishlistItem> items;

    public Wishlist(long id, LocalDate dueDate, Visibility visibility, Profile profile) {
        this.id = id;
        this.dueDate = dueDate;
        this.visibility = visibility;
        this.profile = profile;
    }

    public Wishlist() {}

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

    public List<WishlistItem> getItems() {
        return items;
    }
    public void setItems(List<WishlistItem> items) {
        this.items = items;
    }

}
