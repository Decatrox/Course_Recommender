package com.sumerge.course_recommender;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.course_recommender.author.Author;
import com.sumerge.course_recommender.author.AuthorGetDTO;
import com.sumerge.course_recommender.author.AuthorPostDTO;
import com.sumerge.course_recommender.author.AuthorRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final String userName = "test username";
    private final String password = "test password";
    private static final String DATE_STRING = "2024-08-06 09:36:24.000";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private final ObjectMapper objectMapper = new ObjectMapper()
            .setDateFormat(dateFormat);

    @BeforeAll
    static void beforeAll(@Autowired AuthorRepository authorRepository) throws ParseException {
        Date birthdate = dateFormat.parse(DATE_STRING);
        Author author = new Author();
        author.setName("name"); author.setEmail("author@gmail.com"); author.setBirthdate(birthdate);
        authorRepository.save(author);
        UUID authorUUID = authorRepository.findAll().getFirst().getId();
    }


    @Test
    void getAuthorIntegrationTest() throws Exception {
        AuthorGetDTO authorGetDTO = new AuthorGetDTO();

        Date birthdate = dateFormat.parse(DATE_STRING);
        authorGetDTO.setName("name"); authorGetDTO.setEmail("author@gmail.com"); authorGetDTO.setBirthdate(birthdate);

        // Everything correct
        mockMvc.perform(get("/authors/{email}", "author@gmail.com")
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(authorGetDTO)));

        //Authorized and report header is false
        mockMvc.perform(get("/authors/{email}", "author@gmail.com")
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "false"))
                .andExpect(status().isUnauthorized());

        //Authorized and report header is empty
        mockMvc.perform(get("/authors/{email}", "author@gmail.com")
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", ""))
                .andExpect(status().isUnauthorized());

        //Authorized and no report header
        mockMvc.perform(get("/authors/{email}", "author@gmail.com")
                        .with(httpBasic(userName, password)))
                .andExpect(status().isUnauthorized());

        //Unauthorized
        mockMvc.perform(get("/authors/{email}", "author@gmail.com")
                        .header("x-validation-report", "true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(authorGetDTO)));

        //Incorrect id
        mockMvc.perform(get("/authors/{email}", "author1234@gmail.com")
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "true"))
                .andExpect(status().isNotFound());
    }


    @Test
    void addAuthorIntegrationTest() throws Exception {
        Date birthdate = dateFormat.parse(DATE_STRING);
        AuthorPostDTO authorPostDTO = new AuthorPostDTO();
        authorPostDTO.setName("name"); authorPostDTO.setEmail("author2@gmail.com"); authorPostDTO.setBirthdate(birthdate);

        //Authorized and report header is true
        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorPostDTO))
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "true"))
                .andExpect(status().isOk());

        //Authorized and report header is false
        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorPostDTO))
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "false"))
                .andExpect(status().isUnauthorized());

        //Authorized and report header is empty
        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorPostDTO))
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", ""))
                .andExpect(status().isUnauthorized());

        //Authorized and no report header
        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorPostDTO))
                        .with(httpBasic(userName, password)))
                .andExpect(status().isUnauthorized());

        //Incorrect Username, Correct Password, Correct report header
        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorPostDTO))
                        .with(httpBasic(userName + " ", password))
                        .header("x-validation-report", "true"))
                .andExpect(status().isUnauthorized());

        //Correct Username, Incorrect Password, Correct Report Header
        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorPostDTO))
                        .with(httpBasic(userName, password + " "))
                        .header("x-validation-report", "true"))
                .andExpect(status().isUnauthorized());

        //Incorrect Username, Incorrect Password, Correct Report Header
        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorPostDTO))
                        .with(httpBasic(userName + " ", password + " "))
                        .header("x-validation-report", "true"))
                .andExpect(status().isUnauthorized());

        //Incorrect All
        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorPostDTO))
                        .with(httpBasic(userName + " ", password + " "))
                        .header("x-validation-report", "false"))
                .andExpect(status().isUnauthorized());

        //Everything correct but blank name
        authorPostDTO.setName("");
        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorPostDTO))
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "true"))
                .andExpect(status().isBadRequest());

        //Everything correct but duplicate email
        authorPostDTO.setName("author");
        authorPostDTO.setEmail("author@gmail.com");
        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorPostDTO))
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "true"))
                .andExpect(status().isConflict());

        //Everything correct but invalid email
        authorPostDTO.setEmail("zehe2t");
        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorPostDTO))
                        .with(httpBasic(userName, password))
                        .header("x-validation-report", "true"))
                .andExpect(status().isBadRequest());
    }
}
