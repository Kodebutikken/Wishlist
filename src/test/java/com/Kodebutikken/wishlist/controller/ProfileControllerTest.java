package com.Kodebutikken.wishlist.controller;

import com.Kodebutikken.wishlist.model.Profile;
import com.Kodebutikken.wishlist.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfileController.class)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfileService profileService;

    @Test
    void login_success() throws Exception {

        Profile mockProfile = new Profile();
        mockProfile.setId(1L);

        when(profileService.login("testuser", "1234")).thenReturn(true);
        when(profileService.getProfileByUsernameOrEmail("testuser"))
                .thenReturn(mockProfile);

        mockMvc.perform(post("/profile/login")
                        .param("username", "testuser")
                        .param("password", "1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlists"));
    }
}