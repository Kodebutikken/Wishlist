package com.Kodebutikken.wishlist.controller;

import com.Kodebutikken.wishlist.model.Wishlist;
import com.Kodebutikken.wishlist.service.ProductService;
import com.Kodebutikken.wishlist.service.WishlistService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WishlistController.class)
class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WishlistService wishlistService;

    @MockitoBean
    private ProductService productService;

    @Test
    void createWishlist_success() throws Exception {

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("profileId", 1L);

        mockMvc.perform(post("/wishlists/create")
                        .session(session)
                        .param("visibility", "PRIVATE"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlists"));

        ArgumentCaptor<Wishlist> captor = ArgumentCaptor.forClass(Wishlist.class);
        verify(wishlistService).createWishlist(captor.capture(), eq(1L));

        Wishlist captured = captor.getValue();
        assertEquals("PRIVATE", captured.getVisibility().name());
    }
}
