package com.Kodebutikken.wishlist.model;

public class WishlistItem {
    private Wishlist wishlist;
    private Product product;
    private int quantity;

    public WishlistItem(Wishlist wishlist, Product product, int quantity) {
        this.wishlist = wishlist;
        this.product = product;
        this.quantity = quantity;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }
    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}