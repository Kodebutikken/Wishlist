package com.Kodebutikken.wishlist.repository;

import com.Kodebutikken.wishlist.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getFloat("price"),
            rs.getString("description"),
            rs.getString("product_url")
    );

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM product ORDER BY name";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    public Product getProductById(Long id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, productRowMapper, id);
    }

    public void createProduct(Product product) {
        String sql = "INSERT INTO product (name, price, description, product_url) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getProductUrl());
    }
}