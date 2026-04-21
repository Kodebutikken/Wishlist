package com.Kodebutikken.wishlist.controller;

import com.Kodebutikken.wishlist.model.Product;
import com.Kodebutikken.wishlist.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public String showProducts(HttpSession session, Model model) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }
        List<Product> products = productService.getProductsByProfileId((Long) session.getAttribute("profileId"));
        model.addAttribute("products", products);
        return "product/products";
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

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }
        Product product = productService.getProductById(id);
        if (product == null || !productService.getProfileIdFromProduct(id).equals(session.getAttribute("profileId"))) {
            return "redirect:/products";
        }
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product,
                                @RequestParam(required = false) Long redirectWishlistId,
                                HttpSession session) {
        if (session.getAttribute("profileId") == null) {
            return "redirect:/profile/login";
        }
        productService.createProduct(product, (Long) session.getAttribute("profileId"));
        if (redirectWishlistId != null) {
            return "redirect:/wishlists/" + redirectWishlistId + "/wish/add";
        }
        return "redirect:/wishlists";
    }
}
