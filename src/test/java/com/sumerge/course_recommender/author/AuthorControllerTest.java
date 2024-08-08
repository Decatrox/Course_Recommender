package com.sumerge.course_recommender.author;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.course_recommender.course.CourseController;
import com.sumerge.course_recommender.course.CourseGetDTO;
import com.sumerge.course_recommender.course.CoursePostDTO;
import com.sumerge.course_recommender.course.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController underTest;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
    }

    @Test
    void shouldCallAddAuthorServiceIfValid() throws Exception {
        String dateString = "2024-08-06 09:36:24.000000";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");

        String name = "Author Name"; String email = "author@gmail.com";
        Date birthdate = dateFormat.parse(dateString);
        AuthorPostDTO authorPostDTO = new AuthorPostDTO();
        authorPostDTO.setName(name); authorPostDTO.setEmail(email); authorPostDTO.setBirthdate(birthdate);

        doNothing().when(authorService).addAuthor(any(AuthorPostDTO.class));

        mockMvc.perform(post("/authors/add").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorPostDTO)))
                .andExpect(status().isOk());

        verify(authorService).addAuthor(any(AuthorPostDTO.class));
    }

    @Test
    void shouldCallAddAuthorServiceWithSameArguments() throws Exception {
        String dateString = "2024-08-06 09:36:24.000000";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");

        String name = "Author Name"; String email = "author@gmail.com";
        Date birthdate = dateFormat.parse(dateString);
        AuthorPostDTO authorPostDTO = new AuthorPostDTO();
        authorPostDTO.setName(name); authorPostDTO.setEmail(email); authorPostDTO.setBirthdate(birthdate);

        ArgumentCaptor<AuthorPostDTO> authorPostDTOArgumentCaptor = ArgumentCaptor.forClass(AuthorPostDTO.class);
        doNothing().when(authorService).addAuthor(authorPostDTOArgumentCaptor.capture());

        mockMvc.perform(post("/authors/add").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorPostDTO)))
                .andExpect(status().isOk());

        verify(authorService).addAuthor(authorPostDTOArgumentCaptor.capture());
    }

    @Test
    void getAuthorByEmail() throws Exception {
        String name = "Author Name"; String email = "author@gmail.com"; Date birthdate = new Date();
        AuthorGetDTO authorGetDTO = new AuthorGetDTO();
        authorGetDTO.setName(name); authorGetDTO.setEmail(email); authorGetDTO.setBirthdate(birthdate);

        org.mockito.Mockito.when(authorService.getAuthorByEmail(email)).thenReturn(authorGetDTO);

        mockMvc.perform(get("/authors/GetByEmail/{email}", email))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(authorGetDTO)));

        verify(authorService).getAuthorByEmail(email);
    }
}