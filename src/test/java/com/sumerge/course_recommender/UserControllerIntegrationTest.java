package com.sumerge.course_recommender;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.course_recommender.user.AppUser;
import com.sumerge.course_recommender.user.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final String userName = "test username";
    private final String password = "test password";
    private final ObjectMapper objectMapper = new ObjectMapper();

    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:latest");

    @DynamicPropertySource
    static void configureMySQLContainer(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        mySQLContainer.start();
    }

    @BeforeAll
    static void beforeAll(@Autowired UserRepository userRepository
            , @Autowired BCryptPasswordEncoder bCryptPasswordEncoder) {

        userRepository.deleteAll();
        AppUser appUser = new AppUser();

        appUser.setUserName("test username");
        appUser.setPassword(bCryptPasswordEncoder.encode("test password"));
        userRepository.save(appUser);
    }


    @Test
    void registerUserIntegrationTest() throws Exception {
        AppUser appUser = new AppUser();
        appUser.setUserName("new name");
        appUser.setPassword("new password");

        //Everything
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appUser))
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "true"))
                .andExpect(status().isOk());

        //Not Authenticated, Everything Correct
        appUser.setUserName("new username 2");
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appUser))
                        .header("x-validation-report", "true"))
                .andExpect(status().isOk());

        //No Report Header
        appUser.setUserName("new username 3");
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appUser)))
                .andExpect(status().isUnauthorized());

        //Duplicate username
        appUser.setUserName(userName);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appUser))
                        .header("x-validation-report", "true"))
                .andExpect(status().isConflict());
    }
}
