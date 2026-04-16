package com.Kodebutikken.wishlist.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String showIndex(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/wishlist/wishlists";
        }
        return "index";
    }

    @GetMapping("/error")
    public String showError() {
        return "error";
    }
}
