package com.Kodebutikken.wishlist.repository;

import com.Kodebutikken.wishlist.model.Visibility;
import com.Kodebutikken.wishlist.model.Wishlist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository repo;

    @Test
    void readAllByProfileId() {
        List<Wishlist> wishlists = repo.getWishlistsByProfileId(1L);
        assertThat(wishlists).isNotNull();
        assertThat(wishlists).isNotEmpty();
        assertThat(wishlists)
                .extracting(Wishlist::getVisibility)
                .contains(Visibility.PUBLIC);
    }

    @Test
    void insertAndReadBack() {
        Wishlist wishlist = new Wishlist();
        wishlist.setName("Test wishlist");
        wishlist.setVisibility(Visibility.PRIVATE);
        repo.createWishlist(wishlist, 1L);
        List<Wishlist> wishlists = repo.getWishlistsByProfileId(1L);
        assertThat(wishlists).isNotEmpty();
        assertThat(wishlists)
                .extracting(Wishlist::getVisibility)
                .contains(Visibility.PRIVATE);
    }
}