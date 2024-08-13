package com.sumerge.course_recommender.user;

import com.sumerge.course_recommender.exception_handling.custom_exceptions.UserAlreadyExistsException;
import com.sumerge.course_recommender.mapper.MapStructMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private MapStructMapper mapStructMapper;
    @InjectMocks
    private UserService userService;

    @Test
    void register() {
        UserPostDTO userPostDTO = new UserPostDTO();
        AppUser appUser = new AppUser();
        String userName = "username";
        String password = "password";
        userPostDTO.setUserName(userName); userPostDTO.setPassword(password);
        appUser.setUserName(userName); appUser.setPassword(password);
        String encryptedPassword = bCryptPasswordEncoder.encode(password);

        when(mapStructMapper.userPostDTOToAppUser(userPostDTO)).thenReturn(appUser);
        ArgumentCaptor<AppUser> appUserArgumentCaptor = ArgumentCaptor.forClass(AppUser.class);
        when(userRepository.save(appUserArgumentCaptor.capture())).thenReturn(appUserArgumentCaptor.capture());

        userService.register(userPostDTO);

        AppUser savedAppUser = appUserArgumentCaptor.getValue();
        assertThat(savedAppUser.getUserName()).isEqualTo(userName);
        assertThat(savedAppUser.getPassword()).isEqualTo(encryptedPassword);

        //User already exists
        when(userRepository.existsByUserName(userName)).thenReturn(true);
        assertThatThrownBy(() -> userService.register(userPostDTO))
                .isInstanceOf(UserAlreadyExistsException.class);

    }
}