package com.Kodebutikken.wishlist.repository;

import com.Kodebutikken.wishlist.model.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository repo;

    @Test
    void createProfile_andVerifyLogin() {

        Profile profile = new Profile();
        profile.setUserName("newuser");
        profile.setEmail("new@test.dk");
        profile.setPassword("1234");

        repo.createProfile(profile);

        assertThat(repo.verifyLogin("newuser", "1234")).isTrue();
        assertThat(repo.verifyLogin("newuser", "wrongpassword")).isFalse();
    }

    @Test
    void verifyLogin_failsWithWrongPassword() {

        boolean loginOk = repo.verifyLogin("testuser", "wrongpassword");

        assertThat(loginOk).isFalse();
    }
}