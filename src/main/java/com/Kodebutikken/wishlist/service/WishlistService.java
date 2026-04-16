package com.Kodebutikken.wishlist.service;


import com.Kodebutikken.wishlist.model.Wishlist;
import com.Kodebutikken.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public List<Wishlist> getWishlistsByProfileId(Long profileId) {
        return wishlistRepository.getWishlistsByProfileId(profileId);
    }

    public Wishlist getWishlistById(Long id) {
        return wishlistRepository.getWishlistById(id);
    }

    public void createWishlist(Wishlist wishlist, Long profileId) {
        wishlistRepository.createWishlist(wishlist, profileId);
    }

    public void deleteWishlist(Long id) {
        wishlistRepository.deleteWishlist(id);
    }

    public void updateWishlist(Wishlist wishlist) {
        wishlistRepository.updateWishlist(wishlist);
    }

    public void addProductToWishlist(Long wishlistId, Long productId, int quantity) {
        wishlistRepository.addProductToWishlist(wishlistId, productId, quantity);
    }

    public void removeProductFromWishlist(Long wishlistId, Long productId) {
        wishlistRepository.removeProductFromWishlist(wishlistId, productId);
    }
}
