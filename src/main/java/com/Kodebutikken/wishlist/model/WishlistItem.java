package com.Kodebutikken.wishlist.model;

public class WishlistItem {
    private Product product;
    private int quantity;
    private boolean reserved;
    private Long reservedBy;

    public WishlistItem(Product product, int quantity, boolean reserved, Long reservedBy) {
        this.product = product;
        this.quantity = quantity;
        this.reserved = reserved;
        this.reservedBy = reservedBy;
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

    public boolean getReserved() {
        return reserved;
    }
    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public Long getReservedBy() {
        return reservedBy;
    }
    public void setReservedBy(Long reservedBy) {
        this.reservedBy = reservedBy;
    }
}
