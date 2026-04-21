package com.Kodebutikken.wishlist.controller;

import com.Kodebutikken.wishlist.model.Profile;
import com.Kodebutikken.wishlist.service.ProfileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/login")
    public String showLogin(HttpSession session) {
        if (session.getAttribute("profileId") != null) {
            return "redirect:/wishlist/wishlists";
        }
        return "auth/login";
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegister(HttpSession session, Model model) {
        if (session.getAttribute("profileId") != null) {
            return "redirect:/wishlist/wishlists";
        }
        Profile profile = new Profile();
        model.addAttribute("profile", profile);
        model.addAttribute("username", profile.getUserName());
        model.addAttribute("email", profile.getEmail());
        model.addAttribute("password", profile.getPassword());
        return "auth/register";
    }

    @GetMapping()
    public String showProfile(HttpSession session, Model model) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }
        Long profileId = (Long) session.getAttribute("profileId");
        Profile profile = profileService.getProfileById(profileId);
        model.addAttribute("profile", profile);
        return "profile/profile";
    }

    @GetMapping("/update")
    public String showUpdateProfile(HttpSession session, Model model) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }
        Long profileId = (Long) session.getAttribute("profileId");
        Profile profile = profileService.getProfileById(profileId);
        model.addAttribute("profile", profile);
        return "profile/update";
    }

    @PostMapping("/login")
    public String handleLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model
    ) {
        boolean isAuthenticated = profileService.login(username, password);

        if (!isAuthenticated) {
            model.addAttribute("error", "Forkert brugernavn eller adgangskode.");
            return "auth/login";
        } else {
            Long profileId = profileService.getProfileByUsernameOrEmail(username).getId();
            session.setAttribute("profileId", profileId);
            return "redirect:/wishlists";
        }
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute Profile profile) {
        profileService.createProfile(profile);
        return "redirect:/profile/login";
    }

    @PostMapping("/update")
    public String handleUpdateProfile(
            @ModelAttribute Profile profile,
            @RequestParam() String confirmPassword,
            HttpSession session,
            Model model) {
        String password = profile.getPassword();
        if (password != null && !password.isBlank()) {
            if (!password.equals(confirmPassword)) {
                model.addAttribute("profile", profile);
                model.addAttribute("error", "Adgangskoderne matcher ikke.");
                return "profile/update";
            } else {
                profileService.updatePassword(password, (Long) session.getAttribute("profileId"));
            }
        }

        profileService.updateProfile(profile, (Long) session.getAttribute("profileId"));
        session.setAttribute("profileId", session.getAttribute("profileId"));
        return "redirect:/wishlists";
    }
}
