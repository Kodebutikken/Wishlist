package com.Kodebutikken.wishlist.service;

import com.Kodebutikken.wishlist.model.Profile;
import com.Kodebutikken.wishlist.repository.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public void createProfile(Profile profile) {
        profileRepository.createProfile(profile);
    }

    public boolean login(String username, String password) {
        return profileRepository.verifyLogin(username, password);
    }

    public Profile getProfileByUsername(String username) {
        return profileRepository.getProfileByUsername(username);
    }

    public void updateProfile(Profile profile, String currentUsername) {
        profileRepository.updateProfile(profile, currentUsername);
    }
}
