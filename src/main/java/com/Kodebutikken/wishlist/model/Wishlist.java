package com.Kodebutikken.wishlist.model;

import java.beans.Visibility;
import java.time.LocalDate;
import java.util.List;

public class Wishlist {
    private long id;
    private LocalDate dueDate;
    private Visibility visibility;
    private User user;
    private List<WishlistItem> items;

    public Wishlist(long id, LocalDate dueDate, Visibility visibility, User user) {
        this.id = id;
        this.dueDate = dueDate;
        this.visibility = visibility;
        this.user = user;
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

    public User  getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public List<WishlistItem> getItems() {
        return items;
    }
    public void setItems(List<WishlistItem> items) {
        this.items = items;
    }

}
