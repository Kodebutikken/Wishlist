package com.Kodebutikken.wishlist.controller;

import com.Kodebutikken.wishlist.model.Visibility;
import com.Kodebutikken.wishlist.model.Wishlist;
import com.Kodebutikken.wishlist.service.ProductService;
import com.Kodebutikken.wishlist.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/wishlists")
public class WishlistController {
    //TODO: Implement the wishlist controller to handle creating, viewing, and managing wishlists for users.
    // This will include methods for adding items to a wishlist, viewing a specific wishlist,
    // and possibly sharing wishlists with others.

    private final WishlistService wishlistService;
    private final ProductService productService;

    public WishlistController(WishlistService wishlistService, ProductService productService) {
        this.wishlistService = wishlistService;
        this.productService = productService;
    }

    @GetMapping()
    public String viewWishlists(HttpSession session, Model model) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }

        List<Wishlist> wishlists = wishlistService.getWishlistsByProfileId((Long) session.getAttribute("profileId"));
        model.addAttribute("wishlists", wishlists);

        return "profile/wishlists";
    }

    @GetMapping("/{id}")
    public String viewWishlist(@PathVariable Long id, HttpSession session, Model model) {
        Long profileId = (Long) session.getAttribute("profileId");
        if (profileId == null) {
            return "redirect:/profile/login";
        }
        Wishlist wishlist = wishlistService.getWishlistById(id);
        if (wishlist == null) {
            return "redirect:/wishlists";
        }
        if (!(wishlist.getVisibility() == Visibility.PUBLIC) && !wishlist.getProfileId().equals(profileId)) {
            return "redirect:/wishlists";
        }
        boolean isViewerOwner = wishlist.getProfileId().equals(profileId);

        model.addAttribute("wishlist", wishlist);
        model.addAttribute("viewerId", profileId);
        model.addAttribute("isViewerOwner", isViewerOwner);
        return "profile/wishlist";
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
        return "redirect:/wishlists";
    }

    @GetMapping("/delete/{id}")
    public String deleteWishlist(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }
        Wishlist wishlist = wishlistService.getWishlistById(id);
        if (wishlist == null || !wishlist.getProfileId().equals(session.getAttribute("profileId"))) {
            return "redirect:/wishlists";
        }
        wishlistService.deleteWishlist(id);
        return "redirect:/wishlists";
    }

    @GetMapping("/edit/{id}")
    public String editWishlist(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }
        Wishlist wishlist = wishlistService.getWishlistById(id);
        if (wishlist == null || !wishlist.getProfileId().equals(session.getAttribute("profileId"))) {
            return "redirect:/wishlists";
        }
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("visibilityOptions", Visibility.values());
        return "wishlist/update"; // Return the view name for editing a wishlist
    }

    @PostMapping("/edit")
    public String updateWishlist(@ModelAttribute Wishlist wishlist, HttpSession session) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }
        wishlistService.updateWishlist(wishlist);
        return "redirect:/wishlists";
    }

    @GetMapping("/{wishlistId}/wish/add")
    public String showAddWishForm(@PathVariable Long wishlistId, HttpSession session, Model model) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }
        model.addAttribute("wishlistId", wishlistId);
        model.addAttribute("products", productService.getProductsByProfileId((Long) session.getAttribute("profileId")));
        return "wishlist/addWish";
    }

    @PostMapping("/{wishlistId}/wish/add")
    public String addProductToWishlist(@PathVariable Long wishlistId,
                                       @RequestParam Long productId,
                                       @RequestParam int quantity,
                                       HttpSession session) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }
        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);
        if (wishlist == null || !wishlist.getProfileId().equals(session.getAttribute("profileId"))) {
            return "redirect:/wishlists";
        }
        wishlistService.addProductToWishlist(wishlistId, productId, quantity);
        return "redirect:/wishlists/" + wishlistId;
    }

    @GetMapping("/{wishlistId}/wish/remove/{productId}")
    public String removeProductFromWishlist(@PathVariable Long wishlistId,
                                            @PathVariable Long productId,
                                            HttpSession session) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }
        wishlistService.removeProductFromWishlist(wishlistId, productId);
        return "redirect:/wishlists/" + wishlistId;
    }

    @PostMapping("/{wishlistId}/reserve/{productId}")
    public String reserveProduct(@PathVariable Long wishlistId, @PathVariable Long productId, HttpSession session) {
        Long profileId = (Long) session.getAttribute("profileId");
        if (profileId == null) {
            return "redirect:/profile/login";
        }
        wishlistService.reserveWish(productId, wishlistId, profileId);
        return "redirect:/wishlists/" + wishlistId;
    }

    @PostMapping("/{wishlistId}/unreserve/{productId}")
    public String unreserveProduct(@PathVariable Long wishlistId, @PathVariable Long productId, HttpSession session) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }
        wishlistService.unreserveWish(productId, wishlistId);
        return "redirect:/wishlists/" + wishlistId;
    }

    @GetMapping("/{id}/share")
    public String shareWishlist(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }
        Wishlist wishlist = wishlistService.getWishlistById(id);
        if (wishlist == null || !wishlist.getProfileId().equals(session.getAttribute("profileId"))) {
            return "redirect:/wishlists";
        }
        wishlistService.shareWishlist(id, Visibility.PUBLIC);
        return "redirect:/wishlists/" + id;
    }
}
