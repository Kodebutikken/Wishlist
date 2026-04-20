package com.Kodebutikken.wishlist.repository;

import com.Kodebutikken.wishlist.model.Product;
import com.Kodebutikken.wishlist.model.Visibility;
import com.Kodebutikken.wishlist.model.Wishlist;
import com.Kodebutikken.wishlist.model.WishlistItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishlistRepository {
    private final JdbcTemplate jdbcTemplate;
    public WishlistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Wishlist> wishlistRowMapper = (rs, rowNum) -> {
        Wishlist wishlist = new Wishlist();
        wishlist.setId(rs.getLong("id"));
        wishlist.setName(rs.getString("name"));
        if (rs.getDate("due_date") != null) {
            wishlist.setDueDate(rs.getDate("due_date").toLocalDate());
        }
        wishlist.setVisibility(Visibility.valueOf(rs.getString("visibility"))
        );
        wishlist.setProfileId(rs.getLong("profile_id"));
        return wishlist;
    };

    public List<Wishlist> getWishlistsByProfileId(Long profile_id) {
        String sql = "SELECT * FROM wishlist WHERE profile_id = ?";
        return jdbcTemplate.query(sql, wishlistRowMapper, profile_id);
    }

    private Wishlist getSingleWishlist(String sql, Object param) {
        return jdbcTemplate.queryForObject(sql, wishlistRowMapper, param);
    }

    public void removeProductFromWishlist(Long wishlistId, Long productId) {
        String sql = "DELETE FROM wishlist_item WHERE wishlist_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, wishlistId, productId);
    }

    public void updateQuantityInWishlist(Long wishlistId, Long productId, int quantity) {
        String sql = "UPDATE wishlist_item SET quantity = ? WHERE wishlist_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, quantity, wishlistId, productId);
    }

    public void createWishlist(Wishlist wishlist, Long profile_id) {
        String sql = "INSERT INTO wishlist (name, due_date, visibility, profile_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                wishlist.getName(),
                wishlist.getDueDate(),
                wishlist.getVisibility().name(),
                profile_id);
    }

    public void deleteWishlist(long id) {
        String sql = "DELETE FROM wishlist WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void updateWishlist(Wishlist wishlist) {
        String sql = "UPDATE wishlist SET due_date = ?, visibility = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                wishlist.getDueDate(),
                wishlist.getVisibility().name(),
                wishlist.getId());
    }

    public Wishlist getWishlistWithItems(Long id) {
        Wishlist wishlist = getWishlistById(id);
        String sql = "SELECT wi.quantity, p.id, p.name, p.price, p.description, p.product_url FROM wishlist_item wi JOIN product p ON wi.product_id = p.id WHERE wi.wishlist_id = ? ";
        List<WishlistItem> items = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Product product = new Product(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getFloat("price"),
                    rs.getString("description"),
                    rs.getString("product_url")
            );
            return new WishlistItem(product, rs.getInt("quantity"));
        }, id);
        wishlist.setItems(items);
        return wishlist;
    }

    public void addProductToWishlist(Long wishlistId, Long productId, int quantity) {
        String sql = """
            INSERT INTO wishlist_item (wishlist_id, product_id, quantity)
            VALUES (?, ?, ?)
            ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)
        """;
        jdbcTemplate.update(sql, wishlistId, productId, quantity);
    }

    private Wishlist getWishlistById(long id) {
        String sql = "SELECT * FROM wishlist WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, wishlistRowMapper, id);
    }
}
