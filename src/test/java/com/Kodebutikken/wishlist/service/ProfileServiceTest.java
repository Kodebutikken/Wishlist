package com.Kodebutikken.wishlist.service;


import com.Kodebutikken.wishlist.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private ProfileRepository repository;

    @InjectMocks
    private ProfileService service;

    @Test
    void createProfile() {
        service.createProfile(null);
        verify(repository).createProfile(null);
    }

    @Test
    void loginSucces() {
        when(repository.verifyLogin("test", "123")).thenReturn(true);
        boolean result = service.login("test", "123");
        assertEquals(true, result);
        verify(repository).verifyLogin("test", "123");
    }
}
