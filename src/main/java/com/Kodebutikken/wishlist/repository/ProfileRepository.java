package com.Kodebutikken.wishlist.repository;

import com.Kodebutikken.wishlist.model.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileRepository {
    private final JdbcTemplate jdbcTemplate;
    public ProfileRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Profile> profileQueryMapper = (rs, rowNum) -> {
        Profile profile = new Profile();
        profile.setId(rs.getLong("id"));
        profile.setUserName(rs.getString("username"));
        profile.setEmail(rs.getString("email"));
        profile.setPassword(rs.getString("password"));
        return profile;
    };

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
        return jdbcTemplate.queryForObject(sql, profileQueryMapper, username);
    }

    public Profile getProfileById(Long id) {
        String sql = "SELECT * FROM profile WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, profileQueryMapper, id);
    }

    public void updateProfile(Profile profile, Long profileId) {
        String sql = "UPDATE profile SET email = ?, password = ?, username = ? WHERE id = ?";
        jdbcTemplate.update(sql, profile.getEmail(), profile.getPassword(), profile.getUserName(), profileId);
    }
}
