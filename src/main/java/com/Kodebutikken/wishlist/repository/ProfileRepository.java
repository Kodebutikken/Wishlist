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
        String sql = "INSERT INTO profile (username, email, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, profile.getUserName(), profile.getEmail(), profile.getPassword());
    }
    public boolean verifyLogin(String username, String password) {
        String sql = "SELECT COUNT(*) FROM profile WHERE (username = ? OR email = ?) AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, username, password);

        return count != null && count > 0;
    }

    public Profile getProfileByUsername(String username) {
        String sql = "SELECT * FROM profile WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Profile profile = new Profile();
            profile.setId(rs.getLong("id"));
            profile.setUserName(rs.getString("username"));
            profile.setEmail(rs.getString("email"));
            profile.setPassword(rs.getString("password"));
            return profile;
        }, username);
    }

    public void updateProfile(Profile profile, String currentUsername) {
        String sql = "UPDATE profile SET email = ?, password = ?, username = ? WHERE username = ?";
        jdbcTemplate.update(sql, profile.getEmail(), profile.getPassword(), profile.getUserName(), currentUsername);
    }
}
