package com.Kodebutikken.wishlist.repository;

import com.Kodebutikken.wishlist.exception.DatabaseOperationException;
import com.Kodebutikken.wishlist.exception.InvalidProfileException;
import com.Kodebutikken.wishlist.exception.WishlistNotFoundException;
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
        wishlist.setImageUrl(rs.getString("image_url"));
        if (rs.getDate("due_date") != null) {
            wishlist.setDueDate(rs.getDate("due_date").toLocalDate());
        }
        wishlist.setVisibility(Visibility.valueOf(rs.getString("visibility"))
        );
        wishlist.setProfileId(rs.getLong("profile_id"));
        return wishlist;
    };

    private final RowMapper<Wishlist> wishlistWithCountRowMapper = (rs, rowNum) -> {
        Wishlist wishlist = wishlistRowMapper.mapRow(rs, rowNum);
        wishlist.setItemCount(rs.getInt("item_count"));
        return wishlist;
    };

    public List<Wishlist> getWishlistsByProfileId(Long profile_id) {
        String sql = """
            SELECT w.*, COUNT(wi.product_id) AS item_count FROM wishlist w
            LEFT JOIN wishlist_item wi ON w.id = wi.wishlist_id
            WHERE w.profile_id = ? GROUP BY w.id
        """;
        try {
            return jdbcTemplate.query(sql, wishlistWithCountRowMapper, profile_id);
        } catch (Exception e) {
            throw new WishlistNotFoundException("Ingen ønskelister fundet for profil med id " + profile_id);
        }
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
        String sql = "INSERT INTO wishlist (name, image_url, due_date, visibility, profile_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                wishlist.getName(),
                wishlist.getImageUrl(),
                wishlist.getDueDate(),
                wishlist.getVisibility().name(),
                profile_id);
    }

    public void deleteWishlist(long id) {
        String sql = "DELETE FROM wishlist WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void updateWishlist(Wishlist wishlist) {
        String sql = "UPDATE wishlist SET image_url = ?, due_date = ?, visibility = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                wishlist.getImageUrl(),
                wishlist.getDueDate(),
                wishlist.getVisibility().name(),
                wishlist.getId());
    }

    public Wishlist getWishlistWithItems(Long id) {
        Wishlist wishlist = getWishlistById(id);

        if(wishlist == null) {
            throw new WishlistNotFoundException("Ønskelisten med id " + id + " blev ikke fundet.");
        }
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
        try {
            jdbcTemplate.update(sql, wishlistId, productId, quantity);
        } catch (Exception e) {
            throw new InvalidProfileException("Du har ikke tilladelse til at tilføje produkter til denne ønskeliste.");
        }
    }

    public void updateWishlistVisibility(Long id, Visibility visibility) {
        String sql = "UPDATE wishlist SET visibility = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sql, visibility.name(), id);
        } catch (Exception e) {
            throw new DatabaseOperationException("Synligheden på ønskelisten med id: " + id + " Kunne ikke opdateres.");
        }
    }

    public Wishlist getWishlistById(long id) {
        String sql = "SELECT * FROM wishlist WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, wishlistRowMapper, id);
        } catch (Exception e) {
            throw new WishlistNotFoundException("Ønskelisten med id: " + id + " blev ikke fundet.");
        }
    }
}
