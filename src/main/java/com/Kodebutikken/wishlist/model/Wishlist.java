package com.Kodebutikken.wishlist.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Wishlist {
    private Long id;
    private String name;
    private LocalDate dueDate;
    private Visibility visibility;
    private Long profileId;
    private List<WishlistItem> items = new ArrayList<>();

    public Wishlist(long id, String name, LocalDate dueDate, Visibility visibility, Long profileId) {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.visibility = visibility;
        this.profileId = profileId;
    }

    public Wishlist() {}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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

    public Long getProfileId() {
        return profileId;
    }
    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public List<WishlistItem> getItems() {
        return items;
    }
    public void setItems(List<WishlistItem> items) {
        this.items = items;
    }

}
