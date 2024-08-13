package com.sumerge.course_recommender.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

//Need to add more tests when I change the return true in the class itself
class CustomUserDetailsTest {

    private CustomUserDetails customUserDetails;
    private String testUserName;
    private String testPassword;
    private AppUser user;

    @BeforeEach
    void setUpBeforeClass() {
        user = new AppUser();
        testUserName = "testUser";
        testPassword = "testPassword";
        user.setUserName(testUserName);
        user.setPassword(testPassword);
        customUserDetails = new CustomUserDetails(user);
    }

    @Test
    void getUsername() {
        String userName = customUserDetails.getUsername();
        assertThat(userName).isEqualTo(testUserName);
    }

    @Test
    void isAccountNonExpired() {
        boolean isAccountNonExpired = customUserDetails.isAccountNonExpired();
        assertThat(isAccountNonExpired).isTrue();
    }

    @Test
    void isAccountNonLocked() {
        boolean isAccountNonLocked = customUserDetails.isAccountNonLocked();
        assertThat(isAccountNonLocked).isTrue();
    }

    @Test
    void isCredentialsNonExpired() {
        boolean isCredentialsNonExpired = customUserDetails.isCredentialsNonExpired();
        assertThat(isCredentialsNonExpired).isTrue();
    }

    @Test
    void isEnabled() {
        boolean isEnabled = customUserDetails.isEnabled();
        assertThat(isEnabled).isTrue();
    }

    @Test
    void getPassword() {
        String password = customUserDetails.getPassword();
        assertThat(password).isEqualTo(testPassword);
    }

    @Test
    void getAuthorities() {
        Collection<SimpleGrantedAuthority> expected = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        Collection<SimpleGrantedAuthority> actual = (Collection<SimpleGrantedAuthority>) customUserDetails.getAuthorities();
        assertThat(actual).isEqualTo(expected);

    }
}