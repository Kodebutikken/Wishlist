package com.Kodebutikken.wishlist.repository;

import com.Kodebutikken.wishlist.exception.DatabaseOperationException;
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

    public Long getProfileIdFromProductId(Long id) {
        String sql = "SELECT profile_id FROM product WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, id);
    }

    public void createProduct(Product product, Long profileId) {
        String sql = "INSERT INTO product (name, price, description, product_url, profile_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getProductUrl(),
                profileId);
    }

    public List<Product> getProductsByProfileId(Long id) {
        String sql = "SELECT * FROM product WHERE profile_id = ?";
        return jdbcTemplate.query(sql, productRowMapper, id);
    }

    public void updateProduct(Product product) {
        String sql = "UPDATE product SET name = ?, price = ?, description = ?, product_url = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sql,
                    product.getName(),
                    product.getPrice(),
                    product.getDescription(),
                    product.getProductUrl(),
                    product.getId());
        } catch (Exception e) {
            throw new DatabaseOperationException("Fejl ved opdatering af produkt med id: " + product.getId());
        }
    }

    public void deleteProduct(Long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}