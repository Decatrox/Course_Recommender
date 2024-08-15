//package com.sumerge.course_recommender;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sumerge.course_recommender.author.Author;
//import com.sumerge.course_recommender.author.AuthorGetDTO;
//import com.sumerge.course_recommender.author.AuthorPostDTO;
//import com.sumerge.course_recommender.author.AuthorRepository;
//import com.sumerge.course_recommender.course.Course;
//import com.sumerge.course_recommender.course.CourseGetDTO;
//import com.sumerge.course_recommender.course.CoursePostDTO;
//import com.sumerge.course_recommender.course.CourseRepository;
//import com.sumerge.course_recommender.user.AppUser;
//import com.sumerge.course_recommender.user.UserRepository;
//import org.jetbrains.annotations.NotNull;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.MediaType;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.testcontainers.containers.MySQLContainer;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//class IntegrationTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private CourseRepository courseRepository;
//
//    private final String userName = "test username";
//    private final String password = "test password";
//    private UUID uuid;
//    private static final String DATE_STRING = "2024-08-06 09:36:24.000";
//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//    private final ObjectMapper objectMapper = new ObjectMapper()
//            .setDateFormat(dateFormat);
//
//    private final Course course = new Course();
//    private final Course courseUnique = new Course();
//
//
//    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest");
//
//    @DynamicPropertySource
//    static void configureMySQLContainer(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
//        registry.add("spring.datasource.username", mySQLContainer::getUsername);
//        registry.add("spring.datasource.password", mySQLContainer::getPassword);
//        mySQLContainer.start();
//
//    }
//
//    @BeforeAll
//    static void beforeAll(@Autowired UserRepository userRepository
//            , @Autowired BCryptPasswordEncoder bCryptPasswordEncoder
//    , @Autowired AuthorRepository authorRepository) throws ParseException {
//
//        userRepository.deleteAll();
//
//        AppUser appUser = new AppUser();
//
//        appUser.setUserName("test username");
//        appUser.setPassword(bCryptPasswordEncoder.encode("test password"));
//        userRepository.save(appUser);
//
//        Date birthdate = dateFormat.parse(DATE_STRING);
//        Author author = new Author();
//        author.setName("name"); author.setEmail("author@gmail.com"); author.setBirthdate(birthdate);
//        authorRepository.save(author);
//        UUID authorUUID = authorRepository.findAll().getFirst().getId();
//    }
//
//    @AfterAll
//    static void afterAll() {
//        mySQLContainer.stop();
//    }
//
//    @BeforeEach
//    public void setUp() {
//        courseRepository.deleteAll();
//        course.setName("course"); course.setDescription("course");
//        course.setCredit(6);
//        courseRepository.save(course);
//        uuid = courseRepository.findAll().getFirst().getId();
//
//        courseUnique.setName("course unique"); courseUnique.setDescription("course unique");
//        courseUnique.setCredit(6);
//        courseRepository.save(courseUnique);
//    }
//
//
//    @Test
//    void addCourseIntegrationTest() throws Exception {
//        CoursePostDTO coursePostDTO = new CoursePostDTO();
//        coursePostDTO.setName("course2"); coursePostDTO.setDescription("course2");
//        coursePostDTO.setCredit(6);
//
//        //Authorized and report header is true
//        mockMvc.perform(post("/courses")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isOk())
//                //the 2 lines below may be modified freely
//                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
//                .andExpect(content().string("Added course: " + coursePostDTO.getName()));
//
//        //Authorized and report header is false
//        mockMvc.perform(post("/courses")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "false"))
//                .andExpect(status().isUnauthorized());
//
//        //Authorized and report header is empty
//        mockMvc.perform(post("/courses")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", ""))
//                .andExpect(status().isUnauthorized());
//
//        //Authorized and no report header
//        mockMvc.perform(post("/courses")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName, password)))
//                .andExpect(status().isUnauthorized());
//
//        //Incorrect Username, Correct Password, Correct report header
//        mockMvc.perform(post("/courses")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName + " ", password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isUnauthorized());
//
//        //Correct Username, Incorrect Password, Correct Report Header
//        mockMvc.perform(post("/courses")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName, password + " "))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isUnauthorized());
//
//        //Incorrect Username, Incorrect Password, Correct Report Header
//        mockMvc.perform(post("/courses")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName + " ", password + " "))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isUnauthorized());
//
//        //Incorrect All
//        mockMvc.perform(post("/courses")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName + " ", password + " "))
//                        .header("x-validation-report", "false"))
//                .andExpect(status().isUnauthorized());
//
//        //Everything correct but duplicate name
//        coursePostDTO.setName("course"); coursePostDTO.setDescription("course3");
//        mockMvc.perform(post("/courses")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isConflict());
//
//        //Everything correct but blank name
//        coursePostDTO.setName(""); coursePostDTO.setDescription("course3");
//        mockMvc.perform(post("/courses")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isBadRequest());
//
//        //Everything correct but negative credit points
//        coursePostDTO.setName("course3"); coursePostDTO.setDescription("course3");
//        coursePostDTO.setCredit(-1);
//        mockMvc.perform(post("/courses")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isBadRequest());
//
//        //Everything correct but credit points > 12
//        coursePostDTO.setName("course2"); coursePostDTO.setDescription("course3");
//        coursePostDTO.setCredit(13);
//        mockMvc.perform(post("/courses")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isBadRequest());
//    }
//
//
//    @Test
//    void updateCourseIntegrationTest() throws Exception {
//        CoursePostDTO coursePostDTO = new CoursePostDTO();
//        coursePostDTO.setName("course2"); coursePostDTO.setDescription("course2");
//        coursePostDTO.setCredit(6);
//
//        //Authorized and report header is true
//        mockMvc.perform(put("/courses/{id}", uuid)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isOk());
//
//        //Authorized and report header is false
//        mockMvc.perform(put("/courses/{id}", uuid)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "false"))
//                .andExpect(status().isUnauthorized());
//
//        //Authorized and report header is empty
//        mockMvc.perform(put("/courses/{id}", uuid)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", ""))
//                .andExpect(status().isUnauthorized());
//
//        //Authorized and no report header
//        mockMvc.perform(put("/courses/{id}", uuid)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName, password)))
//                .andExpect(status().isUnauthorized());
//
//        //Incorrect Username, Correct Password, Correct report header
//        mockMvc.perform(put("/courses/{id}", uuid)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName + " ", password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isUnauthorized());
//
//        //Correct Username, Incorrect Password, Correct Report Header
//        mockMvc.perform(put("/courses/{id}", uuid)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName, password + " "))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isUnauthorized());
//
//        //Incorrect Username, Incorrect Password, Correct Report Header
//        mockMvc.perform(put("/courses/{id}", uuid)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName + " ", password + " "))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isUnauthorized());
//
//        //Incorrect All
//        mockMvc.perform(put("/courses/{id}", uuid)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName + " ", password + " "))
//                        .header("x-validation-report", "false"))
//                .andExpect(status().isUnauthorized());
//
//        //Everything correct but duplicate name
//        coursePostDTO.setName("course unique"); coursePostDTO.setDescription("course3");
//        mockMvc.perform(put("/courses/{id}", uuid)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isConflict());
//
//        //Everything correct but blank name
//        coursePostDTO.setName(""); coursePostDTO.setDescription("course3");
//        mockMvc.perform(put("/courses/{id}", uuid)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isBadRequest());
//
//        //Everything correct but negative credit points
//        coursePostDTO.setName("course3"); coursePostDTO.setDescription("course3");
//        coursePostDTO.setCredit(-1);
//        mockMvc.perform(put("/courses/{id}", uuid)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isBadRequest());
//
//        //Everything correct but credit points > 12
//        coursePostDTO.setName("course2"); coursePostDTO.setDescription("course3");
//        coursePostDTO.setCredit(13);
//        mockMvc.perform(put("/courses/{id}", uuid)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isBadRequest());
//
//        //Everything correct but id does not exist
//        coursePostDTO.setName("course2"); coursePostDTO.setDescription("course3");
//        coursePostDTO.setCredit(6);
//        mockMvc.perform(put("/courses/{id}", UUID.randomUUID())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(coursePostDTO))
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isNotFound());
//    }
//
//
//    @Test
//    void deleteCourseIntegrationTest() throws Exception {
//
//        //Authorized and report header is true
//        mockMvc.perform(delete("/courses/{id}", uuid)
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isOk());
//
//        //Authorized and report header is false
//        mockMvc.perform(delete("/courses/{id}", uuid)
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "false"))
//                .andExpect(status().isUnauthorized());
//
//        //Authorized and report header is empty
//        mockMvc.perform(delete("/courses/{id}", uuid)
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", ""))
//                .andExpect(status().isUnauthorized());
//
//        //Authorized and no report header
//        mockMvc.perform(delete("/courses/{id}", uuid)
//                        .with(httpBasic(userName, password)))
//                .andExpect(status().isUnauthorized());
//
//        //Incorrect Username, Correct Password, Correct report header
//        mockMvc.perform(delete("/courses/{id}", uuid)
//                        .with(httpBasic(userName + " ", password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isUnauthorized());
//
//        //Correct Username, Incorrect Password, Correct Report Header
//        mockMvc.perform(delete("/courses/{id}", uuid)
//                        .with(httpBasic(userName, password + " "))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isUnauthorized());
//
//        //Incorrect Username, Incorrect Password, Correct Report Header
//        mockMvc.perform(delete("/courses/{id}", uuid)
//                        .with(httpBasic(userName + " ", password + " "))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isUnauthorized());
//
//        //Incorrect All
//        mockMvc.perform(delete("/courses/{id}", uuid)
//                        .with(httpBasic(userName + " ", password + " "))
//                        .header("x-validation-report", "false"))
//                .andExpect(status().isUnauthorized());
//
//        //Everything correct but id does not exist
//        mockMvc.perform(delete("/courses/{id}", UUID.randomUUID())
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void getCourseIntegrationTest() throws Exception {
//        CourseGetDTO courseGetDTO = new CourseGetDTO();
//        courseGetDTO.setName("course");
//        courseGetDTO.setDescription("course");
//        courseGetDTO.setCredit(6);
//
//        // Everything correct
//        mockMvc.perform(get("/courses/{id}", uuid)
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().json(objectMapper.writeValueAsString(courseGetDTO)));
//
//        //Authorized and report header is false
//        mockMvc.perform(get("/courses/{id}", uuid)
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "false"))
//                .andExpect(status().isUnauthorized());
//
//        //Authorized and report header is empty
//        mockMvc.perform(get("/courses/{id}", uuid)
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", ""))
//                .andExpect(status().isUnauthorized());
//
//        //Authorized and no report header
//        mockMvc.perform(get("/courses/{id}", uuid)
//                        .with(httpBasic(userName, password)))
//                .andExpect(status().isUnauthorized());
//
//        //Not authorized
//        mockMvc.perform(get("/courses/{id}", uuid)
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().json(objectMapper.writeValueAsString(courseGetDTO)));
//
//        //Incorrect id
//        mockMvc.perform(get("/courses/{id}", UUID.randomUUID())
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isNotFound());
//    }
//
//
//    @Test
//    void recommendCourseIntegrationTest() throws Exception {
//        List<CourseGetDTO> courseGetDTOList = getCourseGetDTOS();
//        Pageable pageable = PageRequest.of(0, 2);
//        Page<CourseGetDTO> pageCourseGetDTO = new PageImpl<>(courseGetDTOList, pageable, 2);
//
//        // Everything correct
//        mockMvc.perform(get("/courses/paged/0")
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().json(objectMapper.writeValueAsString(pageCourseGetDTO)));
//
//        //Authorized and report header is false
//        mockMvc.perform(get("/courses/paged/0")
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "false"))
//                .andExpect(status().isUnauthorized());
//
//        //Authorized and report header is empty
//        mockMvc.perform(get("/courses/paged/0")
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", ""))
//                .andExpect(status().isUnauthorized());
//
//        //Authorized and no report header
//        mockMvc.perform(get("/courses/paged/0")
//                        .with(httpBasic(userName, password)))
//                .andExpect(status().isUnauthorized());
//
//        //Not authorized
//        mockMvc.perform(get("/courses/paged/0")
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().json(objectMapper.writeValueAsString(pageCourseGetDTO)));
//
//        //Page doesn't exist
//        mockMvc.perform(get("/courses/paged/99")
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isNotFound());
//    }
//
//    private @NotNull List<CourseGetDTO> getCourseGetDTOS() {
//        CourseGetDTO courseGetDTO = new CourseGetDTO();
//        CourseGetDTO courseGetDTO2 = new CourseGetDTO();
//        courseGetDTO.setName(course.getName());
//        courseGetDTO.setDescription(course.getDescription());
//        courseGetDTO.setCredit(course.getCredit());
//        courseGetDTO2.setName(courseUnique.getName());
//        courseGetDTO2.setDescription(courseUnique.getDescription());
//        courseGetDTO2.setCredit(courseUnique.getCredit());
//
//        List<CourseGetDTO> courseGetDTOList = new ArrayList<>();
//        courseGetDTOList.add(courseGetDTO);
//        courseGetDTOList.add(courseGetDTO2);
//        return courseGetDTOList;
//    }
//
//
//    @Test
//    void getAuthorIntegrationTest() throws Exception {
//        AuthorGetDTO authorGetDTO = new AuthorGetDTO();
//
//        Date birthdate = dateFormat.parse(DATE_STRING);
//        authorGetDTO.setName("name"); authorGetDTO.setEmail("author@gmail.com"); authorGetDTO.setBirthdate(birthdate);
//
//        // Everything correct
//        mockMvc.perform(get("/authors/{email}", "author@gmail.com")
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().json(objectMapper.writeValueAsString(authorGetDTO)));
//
//        //Authorized and report header is false
//        mockMvc.perform(get("/authors/{email}", "author@gmail.com")
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "false"))
//                .andExpect(status().isUnauthorized());
//
//        //Authorized and report header is empty
//        mockMvc.perform(get("/authors/{email}", "author@gmail.com")
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", ""))
//                .andExpect(status().isUnauthorized());
//
//        //Authorized and no report header
//        mockMvc.perform(get("/authors/{email}", "author@gmail.com")
//                        .with(httpBasic(userName, password)))
//                .andExpect(status().isUnauthorized());
//
//        //Unauthorized
//        mockMvc.perform(get("/authors/{email}", "author@gmail.com")
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().json(objectMapper.writeValueAsString(authorGetDTO)));
//
//        //Incorrect id
//        mockMvc.perform(get("/authors/{email}", "author1234@gmail.com")
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isNotFound());
//    }
//
//
//    @Test
//    void addAuthorIntegrationTest() throws Exception {
//        Date birthdate = dateFormat.parse(DATE_STRING);
//        AuthorPostDTO authorPostDTO = new AuthorPostDTO();
//        authorPostDTO.setName("name"); authorPostDTO.setEmail("author2@gmail.com"); authorPostDTO.setBirthdate(birthdate);
//
//        //Authorized and report header is true
//        mockMvc.perform(post("/authors")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(authorPostDTO))
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isOk());
//
//        //Authorized and report header is false
//        mockMvc.perform(post("/authors")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(authorPostDTO))
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "false"))
//                .andExpect(status().isUnauthorized());
//
//        //Authorized and report header is empty
//        mockMvc.perform(post("/authors")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(authorPostDTO))
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", ""))
//                .andExpect(status().isUnauthorized());
//
//        //Authorized and no report header
//        mockMvc.perform(post("/authors")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(authorPostDTO))
//                        .with(httpBasic(userName, password)))
//                .andExpect(status().isUnauthorized());
//
//        //Incorrect Username, Correct Password, Correct report header
//        mockMvc.perform(post("/authors")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(authorPostDTO))
//                        .with(httpBasic(userName + " ", password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isUnauthorized());
//
//        //Correct Username, Incorrect Password, Correct Report Header
//        mockMvc.perform(post("/authors")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(authorPostDTO))
//                        .with(httpBasic(userName, password + " "))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isUnauthorized());
//
//        //Incorrect Username, Incorrect Password, Correct Report Header
//        mockMvc.perform(post("/authors")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(authorPostDTO))
//                        .with(httpBasic(userName + " ", password + " "))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isUnauthorized());
//
//        //Incorrect All
//        mockMvc.perform(post("/authors")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(authorPostDTO))
//                        .with(httpBasic(userName + " ", password + " "))
//                        .header("x-validation-report", "false"))
//                .andExpect(status().isUnauthorized());
//
//        //Everything correct but blank name
//        authorPostDTO.setName("");
//        mockMvc.perform(post("/authors")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(authorPostDTO))
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isBadRequest());
//
//        //Everything correct but duplicate email
//        authorPostDTO.setName("author");
//        authorPostDTO.setEmail("author@gmail.com");
//        mockMvc.perform(post("/authors")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(authorPostDTO))
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isConflict());
//
//        //Everything correct but invalid email
//        authorPostDTO.setEmail("zehe2t");
//        mockMvc.perform(post("/authors")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(authorPostDTO))
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void registerUserIntegrationTest() throws Exception {
//        AppUser appUser = new AppUser();
//        appUser.setUserName("new name");
//        appUser.setPassword("new password");
//
//        //Everything
//        mockMvc.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(appUser))
//                        .with(httpBasic(userName, password))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isOk());
//
//        //Not Authenticated, Everything Correct
//        appUser.setUserName("new username 2");
//        mockMvc.perform(post("/users")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(objectMapper.writeValueAsString(appUser))
//                    .header("x-validation-report", "true"))
//                .andExpect(status().isOk());
//
//        //No Report Header
//        appUser.setUserName("new username 3");
//        mockMvc.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(appUser)))
//                .andExpect(status().isUnauthorized());
//
//        //Duplicate username
//        appUser.setUserName(userName);
//        mockMvc.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(appUser))
//                        .header("x-validation-report", "true"))
//                .andExpect(status().isConflict());
//    }
//
//}
