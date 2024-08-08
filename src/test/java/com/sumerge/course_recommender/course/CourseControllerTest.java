package com.sumerge.course_recommender.course;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(controllers = CourseController.class)
//@AutoConfigureMockMvc(addFilters = false) //to bypass if there is security implemented
@ExtendWith(MockitoExtension.class)
class CourseControllerTest {
    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController underTest;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
    }


    @Test
    void shouldCallRecommendCourse() throws Exception {
        int pageNumber = 1;
        Page<Course> page = getCourses(pageNumber);

        org.mockito.Mockito.when(courseService.getRecommendedCourses(pageNumber)).thenReturn(page);
        mockMvc.perform(get("/courses/discover/{pageNumber}", pageNumber))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(page)));

        verify(courseService).getRecommendedCourses(pageNumber);
    }

    @Test
    void shouldViewCourseById() throws Exception {
        UUID id = UUID.randomUUID();
        //setting the DTO
        CourseGetDTO courseGetDTO = new CourseGetDTO();
        String name = "Test Name"; String description = "Test Description"; int credit = 6;
        courseGetDTO.setName(name); courseGetDTO.setDescription(description); courseGetDTO.setCredit(credit);
        org.mockito.Mockito.when(courseService.viewCourse(id)).thenReturn(courseGetDTO);

        mockMvc.perform(get("/courses/view/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(courseGetDTO)));

        verify(courseService).viewCourse(id);
    }

    @Test
    void shouldCallUpdateCourseService() throws Exception {
        CoursePostDTO coursePostDTO = new CoursePostDTO();
        UUID testCourseId = UUID.randomUUID();
        doNothing().when(courseService).updateCourse(eq(testCourseId), any(CoursePostDTO.class));

        mockMvc.perform(put("/courses/update/{id}", testCourseId).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Updated Course with id: " + testCourseId + " to become\n" + coursePostDTO.toString()));

        verify(courseService).updateCourse(eq(testCourseId), any(CoursePostDTO.class));
    }

    @Test
    void shouldCallUpdateCourseServiceWithSameArguments() throws Exception {
        CoursePostDTO coursePostDTO = new CoursePostDTO();
        String name = "Test Name"; String description = "Test Description"; int credit = 6;
        UUID testCourseId = UUID.randomUUID();
        coursePostDTO.setName(name); coursePostDTO.setDescription(description); coursePostDTO.setCredit(credit);

        ArgumentCaptor<CoursePostDTO> coursePostDTOArgumentCaptor = ArgumentCaptor.forClass(CoursePostDTO.class);
        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);

        doNothing().when(courseService).updateCourse(uuidArgumentCaptor.capture(), coursePostDTOArgumentCaptor.capture());

        mockMvc.perform(put("/courses/update/{id}", testCourseId).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Updated Course with id: " + testCourseId + " to become\n" + coursePostDTO.toString()));

        verify(courseService).updateCourse(uuidArgumentCaptor.capture(), coursePostDTOArgumentCaptor.capture());

    }

    @Test
    void shouldCallDeleteCourseByIdService() throws Exception {
        UUID testCourseId = UUID.randomUUID();
        doNothing().when(courseService).deleteCourse(testCourseId);

        mockMvc.perform(delete("/courses/delete/{id}", testCourseId))
                .andExpect(status().isOk()).andExpect(content().string("Deleted Course with id " + testCourseId));

        verify(courseService).deleteCourse(testCourseId);
    }

    @Test
    void shouldCallAddCourseServiceIfValid() throws Exception {
        CoursePostDTO coursePostDTO = new CoursePostDTO();
        String name = "Test Name"; String description = "Test Description"; int credit = 6;
        coursePostDTO.setName(name); coursePostDTO.setDescription(description); coursePostDTO.setCredit(credit);
        doNothing().when(courseService).addCourse(any(CoursePostDTO.class));

        mockMvc.perform(post("/courses/add").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(coursePostDTO)))
                .andExpect(status().isOk());

        verify(courseService).addCourse(any(CoursePostDTO.class));
    }


    @Test
    void shouldCallAddCourseServiceWithSameArguments() throws Exception {
        CoursePostDTO coursePostDTO = new CoursePostDTO();
        String name = "Test Name"; String description = "Test Description"; int credit = 6;
        coursePostDTO.setName(name); coursePostDTO.setDescription(description); coursePostDTO.setCredit(credit);

        ArgumentCaptor<CoursePostDTO> coursePostDTOArgumentCaptor = ArgumentCaptor.forClass(CoursePostDTO.class);
        doNothing().when(courseService).addCourse(coursePostDTOArgumentCaptor.capture());

        mockMvc.perform(post("/courses/add").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coursePostDTO)))
                .andExpect(status().isOk());

        verify(courseService).addCourse(coursePostDTOArgumentCaptor.capture());
    }


    private static Page<Course> getCourses(int pageNumber) {
        Course testCourse = new Course();
        Course testCourse2 = new Course();
        String name = "Test Course";
        String description = "Test Description";
        int credit = 8;

        testCourse.setName(name);
        testCourse.setDescription(description);
        testCourse.setCredit(credit);
        testCourse2.setName(name);
        testCourse2.setDescription(description);
        testCourse2.setCredit(credit);

        List<Course> courses = Arrays.asList(testCourse, testCourse2);
        Pageable pageable = PageRequest.of(pageNumber, 2);
        Page<Course> page = new PageImpl<>(courses, pageable, courses.size());
        return page;
    }
}