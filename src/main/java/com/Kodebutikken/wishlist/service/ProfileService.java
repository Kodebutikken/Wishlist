package com.Kodebutikken.wishlist.service;

import com.Kodebutikken.wishlist.exception.ProfileNotFoundException;
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

    public Profile getProfileByUsernameOrEmail(String usernameOrEmail) {
        return profileRepository.getProfileByUsernameOrEmail(usernameOrEmail);
    }

    public Profile getProfileById(Long id) {
        return profileRepository.getProfileById(id);
    }

    public void updateProfile(Profile profile, Long profileId) {
        profileRepository.updateProfile(profile, profileId);
    }
}
