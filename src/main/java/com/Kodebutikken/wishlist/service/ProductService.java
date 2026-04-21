package com.Kodebutikken.wishlist.service;

import com.Kodebutikken.wishlist.model.Product;
import com.Kodebutikken.wishlist.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public Product getProductById(Long id) {
        return productRepository.getProductById(id);
    }

    public Long getProfileIdFromProduct(Long id) {
        return productRepository.getProfileIdFromProductId(id);
    }

    public List<Product> getProductsByProfileId(Long id) {
        return productRepository.getProductsByProfileId(id);
    }

    public void createProduct(Product product, Long profileId) {
        productRepository.createProduct(product, profileId);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteProduct(id);
    }
}