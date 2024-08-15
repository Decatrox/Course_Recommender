package com.sumerge.course_recommender;

import com.sumerge.course_recommender.user.AppUser;
import com.sumerge.course_recommender.user.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class MasterIntegrationTest {

    public static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest");

    static {
            mySQLContainer.start();
        }

        @DynamicPropertySource
        public static void configureMySQLContainer(DynamicPropertyRegistry registry) {
            registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
            registry.add("spring.datasource.username", mySQLContainer::getUsername);
            registry.add("spring.datasource.password", mySQLContainer::getPassword);
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
}