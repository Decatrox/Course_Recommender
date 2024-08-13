package com.sumerge.course_recommender.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;
    @Mock
    private UserRepository userRepository;

    @Test
    void itShouldLoadUserByUsername() {
        AppUser appUser = new AppUser();
        String userName = "username";
        String password = "password";
        appUser.setUserName(userName);
        appUser.setPassword(password);

        when(userRepository.findByUserName(userName)).thenReturn(appUser);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

        assertThat(userDetails.getUsername()).isEqualTo(userName);
        assertThat(userDetails.getPassword()).isEqualTo(password);

        //User doesn't exist
        when(userRepository.findByUserName(userName)).thenReturn(null);
        assertThatThrownBy(() -> customUserDetailsService.loadUserByUsername(userName))
                .isInstanceOf(UsernameNotFoundException.class);
    }

}