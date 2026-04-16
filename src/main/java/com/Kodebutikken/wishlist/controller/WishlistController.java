package com.Kodebutikken.wishlist.controller;

import com.Kodebutikken.wishlist.model.Visibility;
import com.Kodebutikken.wishlist.model.Wishlist;
import com.Kodebutikken.wishlist.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {
    //TODO: Implement the wishlist controller to handle creating, viewing, and managing wishlists for users.
    // This will include methods for adding items to a wishlist, viewing a specific wishlist,
    // and possibly sharing wishlists with others.

    private final WishlistService wishlistService;
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/wishlists")
    public String viewWishlists(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/profile/login"; // Redirect to login if not authenticated
        }


        List<Wishlist> wishlists = wishlistService.getWishlistByProfileId((Long) session.getAttribute("profileId"));
        model.addAttribute("wishlists", wishlists);

        return "/profile/wishlists"; // Return the view name for displaying wishlists
    }

    @GetMapping("/create")
    public String showCreateWishlistForm(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/profile/login"; // Redirect to login if not authenticated
        }
        Wishlist wishlist = new Wishlist();
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("visibilityOptions", Visibility.values());
        model.addAttribute("userId", session.getAttribute("profileId"));
        return "wishlist/create"; // Return the view name for creating a wishlist
    }



    @PostMapping("/create")
    public String createWishlist(@ModelAttribute Wishlist wishlist, HttpSession session) {
        if (session.getAttribute("user")== null) {
            return "redirect:/profile/login"; // Redirect to login if not authenticated
        }
        wishlistService.createWishlist(wishlist, (Long) session.getAttribute("profileId"));
        return "redirect:/wishlist/wishlists"; // Redirect to the wishlist page after creation
    }

    @GetMapping("/delete/{id}")
    public String deleteWishlist(@PathVariable Long id, HttpSession session) {
        Long profileId = (Long) session.getAttribute("profileId");
        if(profileId == null) {
            return "redirect:/profile/login"; // Redirect to login if not authenticated
        }
        wishlistService.deleteWishlist(id);
        return "redirect:/wishlist"; // Redirect to the wishlist page after deletion
    }

    @GetMapping("/edit/{id}")
    public String editWishlist(@PathVariable Long id, HttpSession session, Model model) {
        Long profileId = (Long) session.getAttribute("profileId");
        if(profileId == null) {
            return "redirect:/profile/login"; // Redirect to login if not authenticated
        }
        model.addAttribute("wishlist", wishlistService.getWishlistById(id));
        return "wishlist/edit"; // Return the view name for editing a wishlist
    }

    @PostMapping("/edit")
    public String updateWishlist(@ModelAttribute Wishlist wishlist, HttpSession session) {
        Long profileId = (Long) session.getAttribute("profileId");
        if(profileId == null) {
            return "redirect:/profile/login"; // Redirect to login if not authenticated
        }
        wishlistService.updateWishlist(wishlist);
        return "redirect:/wishlist"; // Redirect to the wishlist page after updating    
    }
}
