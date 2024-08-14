package com.sumerge.course_recommender.course;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.course_recommender.user.AppUser;
import com.sumerge.course_recommender.user.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CourseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ObjectMapper objectMapper = new ObjectMapper();


    private final String userName = "test username";
    private final String password = "test password";
    private UUID uuid;


    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest");

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

    @AfterAll
    static void afterAll() {
        mySQLContainer.stop();
    }

    @BeforeEach
    public void setUp() {
        courseRepository.deleteAll();
        Course course = new Course();
        course.setName("course"); course.setDescription("course");
        course.setCredit(6);
        courseRepository.save(course);
        uuid = courseRepository.findAll().getFirst().getId();

        Course courseUnique = new Course();
        courseUnique.setName("course unique"); courseUnique.setDescription("course unique");
        courseUnique.setCredit(6);
        courseRepository.save(courseUnique);
    }


    @Test
    public void addCourseTest() throws Exception {
        //Authorized and report header is true
        CoursePostDTO coursePostDTO = new CoursePostDTO();
        coursePostDTO.setName("course2"); coursePostDTO.setDescription("course2");
        coursePostDTO.setCredit(6);

        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "true"))
                .andExpect(status().isOk())
                //the 2 lines below may be modified freely
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Added course: " + coursePostDTO.getName()));

        //Authorized and report header is false
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "false"))
                .andExpect(status().isUnauthorized());

        //Authorized and report header is empty
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", ""))
                .andExpect(status().isUnauthorized());

        //Authorized and no report header
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName, password)))
                .andExpect(status().isUnauthorized());

        //Incorrect Username, Correct Password, Correct report header
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName + " ", password))
                        .header("x-validation-report", "true"))
                .andExpect(status().isUnauthorized());

        //Correct Username, Incorrect Password, Correct Report Header
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName, password + " "))
                        .header("x-validation-report", "true"))
                .andExpect(status().isUnauthorized());

        //Incorrect Username, Incorrect Password, Correct Report Header
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName + " ", password + " "))
                        .header("x-validation-report", "true"))
                .andExpect(status().isUnauthorized());

        //Incorrect All
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName + " ", password + " "))
                        .header("x-validation-report", "false"))
                .andExpect(status().isUnauthorized());

        //Everything correct but duplicate name
        coursePostDTO.setName("course"); coursePostDTO.setDescription("course3");
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "true"))
                .andExpect(status().isConflict());

        //Everything correct but negative credit points
        coursePostDTO.setName("course3"); coursePostDTO.setDescription("course3");
        coursePostDTO.setCredit(-1);
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "true"))
                .andExpect(status().isBadRequest());

        //Everything correct but credit points > 12
        coursePostDTO.setName("course2"); coursePostDTO.setDescription("course3");
        coursePostDTO.setCredit(13);
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "true"))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void updateCourseTest() throws Exception {
        //Authorized and report header is true
        CoursePostDTO coursePostDTO = new CoursePostDTO();
        coursePostDTO.setName("course2"); coursePostDTO.setDescription("course2");
        coursePostDTO.setCredit(6);

        mockMvc.perform(put("/courses/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "true"))
                .andExpect(status().isOk());

        //Authorized and report header is false
        mockMvc.perform(put("/courses/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "false"))
                .andExpect(status().isUnauthorized());

        //Authorized and report header is empty
        mockMvc.perform(put("/courses/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", ""))
                .andExpect(status().isUnauthorized());

        //Authorized and no report header
        mockMvc.perform(put("/courses/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName, password)))
                .andExpect(status().isUnauthorized());

        //Incorrect Username, Correct Password, Correct report header
        mockMvc.perform(put("/courses/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName + " ", password))
                        .header("x-validation-report", "true"))
                .andExpect(status().isUnauthorized());

        //Correct Username, Incorrect Password, Correct Report Header
        mockMvc.perform(put("/courses/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName, password + " "))
                        .header("x-validation-report", "true"))
                .andExpect(status().isUnauthorized());

        //Incorrect Username, Incorrect Password, Correct Report Header
        mockMvc.perform(put("/courses/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName + " ", password + " "))
                        .header("x-validation-report", "true"))
                .andExpect(status().isUnauthorized());

        //Incorrect All
        mockMvc.perform(put("/courses/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName + " ", password + " "))
                        .header("x-validation-report", "false"))
                .andExpect(status().isUnauthorized());

        //Everything correct but duplicate name
        coursePostDTO.setName("course unique"); coursePostDTO.setDescription("course3");
        mockMvc.perform(put("/courses/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "true"))
                .andExpect(status().isConflict());

        //Everything correct but negative credit points
        coursePostDTO.setName("course3"); coursePostDTO.setDescription("course3");
        coursePostDTO.setCredit(-1);
        mockMvc.perform(put("/courses/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "true"))
                .andExpect(status().isBadRequest());

        //Everything correct but credit points > 12
        coursePostDTO.setName("course2"); coursePostDTO.setDescription("course3");
        coursePostDTO.setCredit(13);
        mockMvc.perform(put("/courses/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "true"))
                .andExpect(status().isBadRequest());

        //Everything correct but id does not exist
        coursePostDTO.setName("course2"); coursePostDTO.setDescription("course3");
        coursePostDTO.setCredit(6);
        mockMvc.perform(put("/courses/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO))
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "true"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void deleteCourseTest() throws Exception {
        //Authorized and report header is true

        mockMvc.perform(delete("/courses/{id}", uuid)
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "true"))
                .andExpect(status().isOk());

        //Authorized and report header is false
        mockMvc.perform(delete("/courses/{id}", uuid)
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "false"))
                .andExpect(status().isUnauthorized());

        //Authorized and report header is empty
        mockMvc.perform(delete("/courses/{id}", uuid)
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", ""))
                .andExpect(status().isUnauthorized());

        //Authorized and no report header
        mockMvc.perform(delete("/courses/{id}", uuid)
                        .with(httpBasic(userName, password)))
                .andExpect(status().isUnauthorized());

        //Incorrect Username, Correct Password, Correct report header
        mockMvc.perform(delete("/courses/{id}", uuid)
                        .with(httpBasic(userName + " ", password))
                        .header("x-validation-report", "true"))
                .andExpect(status().isUnauthorized());

        //Correct Username, Incorrect Password, Correct Report Header
        mockMvc.perform(delete("/courses/{id}", uuid)
                        .with(httpBasic(userName, password + " "))
                        .header("x-validation-report", "true"))
                .andExpect(status().isUnauthorized());

        //Incorrect Username, Incorrect Password, Correct Report Header
        mockMvc.perform(delete("/courses/{id}", uuid)
                        .with(httpBasic(userName + " ", password + " "))
                        .header("x-validation-report", "true"))
                .andExpect(status().isUnauthorized());

        //Incorrect All
        mockMvc.perform(delete("/courses/{id}", uuid)
                        .with(httpBasic(userName + " ", password + " "))
                        .header("x-validation-report", "false"))
                .andExpect(status().isUnauthorized());

        //Everything correct but id does not exist
        mockMvc.perform(delete("/courses/{id}", UUID.randomUUID())
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "true"))
                .andExpect(status().isNotFound());
    }

}
