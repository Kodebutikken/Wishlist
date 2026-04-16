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
        if(session.getAttribute("profileId") != null) {
            return "redirect:/wishlist/wishlists"; // Redirect to wishlists if already logged in
        }
        return "auth/login"; // Return the view name for the login page
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegister(HttpSession session, Model model) {
        if(session.getAttribute("profileId") != null) {
            return "redirect:/wishlist/wishlists"; // Redirect to wishlists if already logged in
        }
        Profile profile = new Profile();
        model.addAttribute("profile", profile);
        model.addAttribute("username", profile.getUserName());
        model.addAttribute("email", profile.getEmail());
        model.addAttribute("password", profile.getPassword());
        return "auth/register"; // Return the view name for the registration page
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
        Long profileId = profileService.getProfileByUsername(username).getId();

        if (!isAuthenticated) {
            model.addAttribute("error", "Forkert brugernavn eller adgangskode.");
            return "auth/login";
        } else {
            session.setAttribute("profileId", profileId);
            return "redirect:/wishlist/wishlists";
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
            }
        }

        profileService.updateProfile(profile, (Long) session.getAttribute("profileId"));
        session.setAttribute("profileId", session.getAttribute("profileId"));
        return "redirect:/wishlist/wishlists";
    }
}
