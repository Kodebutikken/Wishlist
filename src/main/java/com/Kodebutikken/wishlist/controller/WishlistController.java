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
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }

        List<Wishlist> wishlists = wishlistService.getWishlistsByProfileId((Long) session.getAttribute("profileId"));
        model.addAttribute("wishlists", wishlists);

        return "profile/wishlists";
    }

    @GetMapping("/create")
    public String showCreateWishlistForm(HttpSession session, Model model) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }
        Long profileId = (Long) session.getAttribute("profileId");
        Wishlist wishlist = new Wishlist();
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("visibilityOptions", Visibility.values());
        model.addAttribute("profileId", profileId);

        return "wishlist/create";
    }

    @PostMapping("/create")
    public String createWishlist(@ModelAttribute Wishlist wishlist, HttpSession session) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }
        wishlistService.createWishlist(wishlist, (Long) session.getAttribute("profileId"));
        return "redirect:/wishlist/wishlists";
    }

    @GetMapping("/delete/{id}")
    public String deleteWishlist(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }
        wishlistService.deleteWishlist(id);
        return "redirect:/wishlist"; // Redirect to the wishlist page after deletion
    }

    @GetMapping("/edit/{id}")
    public String editWishlist(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }
        model.addAttribute("wishlist", wishlistService.getWishlistById(id));
        return "wishlist/edit"; // Return the view name for editing a wishlist
    }

    @PostMapping("/edit")
    public String updateWishlist(@ModelAttribute Wishlist wishlist, HttpSession session) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }
        wishlistService.updateWishlist(wishlist);
        return "redirect:/wishlist/wishlists";
    }
    @PostMapping("/{wishlistId}/wish/add")
    public String addProductToWishlist(@PathVariable Long wishlistId,
                                       @RequestParam Long productId,
                                       @RequestParam int quantity,
                                       HttpSession session) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }
        wishlistService.addProductToWishlist(wishlistId, productId, quantity);
        return "redirect:/wishlist/" + wishlistId;
    }

    @GetMapping("/{wishlistId}/wish/remove/{productId}")
    public String removeProductFromWishlist(@PathVariable Long wishlistId,
                                            @PathVariable Long productId,
                                            HttpSession session) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }
        wishlistService.removeProductFromWishlist(wishlistId, productId);
        return "redirect:/wishlist/" + wishlistId;
    }
}
