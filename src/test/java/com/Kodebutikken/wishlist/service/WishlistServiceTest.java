package com.Kodebutikken.wishlist.service;

import com.Kodebutikken.wishlist.model.Wishlist;
import com.Kodebutikken.wishlist.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WishlistServiceTest {

    @Mock
    private WishlistRepository repository;

    @InjectMocks
    private WishlistService service;

    @Test
    void createWishlist() {
        Wishlist wishlist = new Wishlist();
        service.createWishlist(wishlist, 1L);
        verify(repository).createWishlist(wishlist, 1L);
    }

    @Test
    void addProductToWishlist() {
        service.addProductToWishlist(1L, 2L, 1);
        verify(repository).addProductToWishlist(1L, 2L, 1);
    }
}
