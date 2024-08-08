package com.sumerge.course_recommender.course;

import com.sumerge.course_recommender.mapper.MapStructMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import static org.assertj.core.api.Assertions.*;


import java.util.*;


//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
    @InjectMocks
    private CourseService underTest;

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private MapStructMapper mapStructMapper;
    @Mock
    private CourseRecommender courseRecommender;


    private Course testCourse;
    private final String name = "Test Course";
    private final String description = "Test Description";
    private final int credit = 8;

    @BeforeEach
    void setUp() {
        testCourse = new Course();
        testCourse.setName(name); testCourse.setDescription(description); testCourse.setCredit(credit);
    }


    @Test
    void itShouldGetRecommendedCourses() {
        int pageNumber = 1;
        Course testCourse2 = new Course();
        testCourse2.setName(name); testCourse2.setDescription(description); testCourse2.setCredit(credit);
        List<Course> courses = Arrays.asList(testCourse, testCourse2);
        Page<Course> page = new PageImpl<>(courses);
        org.mockito.Mockito.when(courseRecommender.recommendedCourses(pageNumber)).thenReturn(page);

        underTest.getRecommendedCourses(pageNumber);

        ArgumentCaptor<Integer> pageNumberArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        org.mockito.Mockito.verify(courseRecommender).recommendedCourses(pageNumberArgumentCaptor.capture());
        int queriedPageNumber = pageNumberArgumentCaptor.getValue();

        assertThat(queriedPageNumber).isEqualTo(pageNumber);

    }

    @Test
    void itShouldAddCourse() {
        CoursePostDTO coursePostDTO = new CoursePostDTO();
        coursePostDTO.setName(name); coursePostDTO.setDescription(description); coursePostDTO.setCredit(credit);
        org.mockito.Mockito.when(mapStructMapper.coursePostDTOToCourse(coursePostDTO)).thenReturn(testCourse);

        underTest.addCourse(coursePostDTO);

        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        org.mockito.Mockito.verify(courseRepository).save(courseArgumentCaptor.capture());
        Course savedCourse = courseArgumentCaptor.getValue();

        assertThat(savedCourse).isEqualTo(testCourse);

    }

    @Test
    void itShouldUpdateCourse() {
        UUID id = UUID.randomUUID();
        testCourse.setId(id);
        CoursePostDTO coursePostDTO = new CoursePostDTO();
        coursePostDTO.setName(name); coursePostDTO.setDescription(description); coursePostDTO.setCredit(credit);
        org.mockito.Mockito.when(courseRepository.getReferenceById(id)).thenReturn(testCourse);
        underTest.updateCourse(id, coursePostDTO);

        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        org.mockito.Mockito.verify(courseRepository).save(courseArgumentCaptor.capture());
        Course savedCourse = courseArgumentCaptor.getValue();

        assertThat(savedCourse).isEqualTo(testCourse);
    }

    @Test
    void itShouldViewCourseById() {
        UUID id = UUID.randomUUID();
        testCourse.setId(id);

        //setting the DTO
        CourseGetDTO courseGetDTO = new CourseGetDTO();
        courseGetDTO.setName(name); courseGetDTO.setDescription(description); courseGetDTO.setCredit(credit);
        org.mockito.Mockito.when(courseRepository.findById(id)).thenReturn(Optional.of(testCourse));
        org.mockito.Mockito.when(mapStructMapper.courseToCourseGetDTO(testCourse)).thenReturn(courseGetDTO);

        CourseGetDTO returnedCourse = underTest.viewCourse(id);

        assertThat(returnedCourse).usingRecursiveComparison().ignoringFields("id").isEqualTo(testCourse);

    }

    @Test
    void itShouldDeleteCourse() {
        UUID id = UUID.randomUUID();
        CoursePostDTO coursePostDTO = new CoursePostDTO();
        coursePostDTO.setName(name); coursePostDTO.setDescription(description); coursePostDTO.setCredit(credit);

        org.mockito.Mockito.when(courseRepository.existsById(id)).thenReturn(true);
        org.mockito.Mockito.doNothing().when(courseRepository).deleteById(id);

        underTest.deleteCourse(id);

        ArgumentCaptor<UUID> idArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        org.mockito.Mockito.verify(courseRepository).deleteById(idArgumentCaptor.capture());
        UUID deletedID = idArgumentCaptor.getValue();

        assertThat(deletedID).isEqualTo(id);
    }

    private Course createTestCourse(int num) {
        Course course = new Course();
        course.setName("Test Course " + num);
        course.setDescription("Test Description " + num);
        course.setCredit(num);
        return course;
    }
}