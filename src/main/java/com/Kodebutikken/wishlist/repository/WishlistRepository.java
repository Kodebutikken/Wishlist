package com.Kodebutikken.wishlist.repository;

import com.Kodebutikken.wishlist.model.Product;
import com.Kodebutikken.wishlist.model.Profile;
import com.Kodebutikken.wishlist.model.Visibility;
import com.Kodebutikken.wishlist.model.Wishlist;
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
        if (rs.getDate("due_date") != null) {
            wishlist.setDueDate(rs.getDate("due_date").toLocalDate());
        }
        wishlist.setVisibility(Visibility.valueOf(rs.getString("visibility")));
        Profile profile = new Profile();
        profile.setId(rs.getLong("profile_id"));
        wishlist.setProfile(profile);
        return wishlist;
    };

    public List<Wishlist> getWishlistsByProfileId(Long profile_id) {
        String sql = "SELECT * FROM wishlist WHERE profile_id = ?";
        return jdbcTemplate.query(sql, wishlistRowMapper, profile_id);
    }

    public void createWishlist(Wishlist wishlist, Long profile_id) {
        String sql = "INSERT INTO wishlist (due_date, visibility, profile_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                wishlist.getDueDate(),
                wishlist.getVisibility().name(),
                profile_id);
        jdbcTemplate.update(sql, wishlist.getDueDate(), wishlist.getVisibility().name(), profile_id);
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

    private Wishlist getSingleWishlist(String sql, Object param) {
        Wishlist wishlist = jdbcTemplate.queryForObject(sql, wishlistRowMapper, param);
        if(wishlist != null) {
            wishlist.setProducts(getProductsForWishlist(wishlist.getId()));
        }
        return wishlist;
    }

    public Wishlist getWishlistById(Long id) {
        return getSingleWishlist("SELECT * FROM wishlist WHERE id = ?", id);
    }

    public List<Product> getProductsForWishlist(Long wishlistId) {
        String sql = "SELECT p.id, p.name, p.price FROM product p JOIN wishlist_item wi ON p.id = wi.product_id WHERE wi.wishlist_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Product(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getFloat("price")
            ), wishlistId);

    }

    public void addProductToWishlist(Long wishlistId, Long productId) {
        String checkSql = "SELECT COUNT(*) FROM wishlist_item WHERE wishlist_id = ? AND product_id = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, wishlistId, productId);
        if (count != null && count > 0) {
            return;
        }
        String sql = "INSERT INTO wishlist_item (wishlist_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, wishlistId, productId);
    }

    public void removeProductFromWishlist(Long wishlistId, Long productId) {
        String sql = "DELETE FROM wishlist_item WHERE wishlist_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, wishlistId, productId);
    }

}






