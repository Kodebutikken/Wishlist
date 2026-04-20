package com.Kodebutikken.wishlist.controller;

import com.Kodebutikken.wishlist.model.Product;
import com.Kodebutikken.wishlist.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/create")
    public String showCreateProductForm(HttpSession session,
                                        @RequestParam(required = false) Long redirectWishlistId,
                                        Model model) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }

        model.addAttribute("product", new Product());
        model.addAttribute("redirectWishlistId", redirectWishlistId);
        return "product/create";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product,
                                @RequestParam(required = false) Long redirectWishlistId,
                                HttpSession session) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }

        productService.createProduct(product);

        if (redirectWishlistId != null) {
            return "redirect:/wishlists/" + redirectWishlistId + "/wish/add";
        }

        return "redirect:/wishlists";
    }
}
