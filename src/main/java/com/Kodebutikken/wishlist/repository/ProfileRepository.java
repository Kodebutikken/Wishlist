package com.Kodebutikken.wishlist.repository;

import com.Kodebutikken.wishlist.model.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProfileRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createProfile(Profile profile) {
        String sql = "INSERT INTO profiles (user_name, email, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, profile.getUserName(), profile.getEmail(), profile.getPassword());
    }


    public void verifyLogin(String username, String password) {
        String sql = "SELECT COUNT(*) FROM profiles WHERE user_name = ? AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, password);
        if (count != null && count > 0) {
            System.out.println("Login successful for user: " + username);
        } else {
            System.out.println("Login failed for user: " + username);
        }
    }
}
