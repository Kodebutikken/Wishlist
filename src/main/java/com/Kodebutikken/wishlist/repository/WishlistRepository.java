package com.Kodebutikken.wishlist.repository;

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
        //VED IKKE HVORDAN JEG GØR MED DATO
        wishlist.setVisibility(Visibility.valueOf(rs.getString("visibility"))
        );
        return wishlist;
    };

    private Wishlist getSingleWishlist(String sql, Object param) {
        return jdbcTemplate.queryForObject(sql, wishlistRowMapper, param);
    }

    public Wishlist getWishlistById(long id) {
        return getSingleWishlist("SELECT * FROM wishlist WHERE id = ?", id);
    }

    public void createWishlist(Wishlist wishlist, Long userId) {
        String sql = "INSERT INTO wishlist (due_date, visibility, user_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                wishlist.getDueDate(),
                wishlist.getVisibility().name(),
                userId);
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

}
