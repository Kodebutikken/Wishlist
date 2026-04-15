package com.Kodebutikken.wishlist.controller;

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

    @GetMapping("/wishlists")
    public String viewWishlists() {
        // Logic to retrieve and display the user's wishlists
        return "profile/wishlists"; // Return the view name for displaying wishlists
    }

    @GetMapping("/login")
    public String showLogin(HttpSession session) {
        if(session.getAttribute("user") != null) {
            return "redirect:/profile/wishlists"; // Redirect to wishlists if already logged in
        }
        return "login"; // Return the view name for the login page
    }

    @GetMapping("/register")
    public String showRegister() {
        // Logic to show the user registration page
        return "profile/register"; // Return the view name for the registration page
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
            model.addAttribute("error", "Invalid username or password");
            return "login"; // Return to the login page with an error message
        } else {
            session.setAttribute("user", username); // Store the username in the session
            return "redirect:/profiles/wishlists"; // Redirect to the wishlists page after successful login
        }
    }

    @PostMapping("/register")
    public String handleRegister() {
        // Logic to handle user registration
        return "redirect:/profile/login"; // Redirect to the login page after successful registration
    }
}
