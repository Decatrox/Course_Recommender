//package com.sumerge.course_recommender.author;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(MockitoExtension.class)
//@WebMvcTest(controllers = AuthorController.class)
//public class AuthorIntegrationTest {
//
//    @Mock
//    private AuthorRepository authorRepository;
//
//    @MockBean
//    private AuthorService authorService;
//
////    @InjectMocks
////    private AuthorController underTest;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
////    @BeforeEach
////    public void setUp() {
////        mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
////    }
//
//    //Integration Test
//    @Test
//    void itShouldNotAddDuplicateAuthor() throws Exception {
//        String dateString = "2024-08-06 09:36:24.000000";
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
//        String name = "Author Name"; String email = "author@gmail.com";
//        Date birthdate = dateFormat.parse(dateString);
//        AuthorPostDTO authorPostDTO = new AuthorPostDTO();
//        authorPostDTO.setName(name); authorPostDTO.setEmail(email); authorPostDTO.setBirthdate(birthdate);
//
//        org.mockito.Mockito.when(authorRepository.existsByEmail(any(String.class))).thenReturn(true);
//
//        mockMvc.perform(post("/authors")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(authorPostDTO)))
//                .andExpect(status().isConflict());
//    }
//}
