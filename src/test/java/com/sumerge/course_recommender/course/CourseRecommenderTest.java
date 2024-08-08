package com.sumerge.course_recommender.course;

import com.sumerge.course_recommender.course.recommenders.CourseRecommenderFun;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@Disabled
class CourseRecommenderTest {

    @MockBean
    private CourseRepository courseRepository;

//    @Autowired
    private CourseRecommender underTest;

    @Test
    void recommendedCourses() {
        int pageNumber = 1;
        Page<Course> page = getCourses();

        when(courseRepository.findAll(PageRequest.of(pageNumber, 2))).thenReturn(page);

        Page<Course> result = underTest.recommendedCourses(pageNumber);

        assertThat(result).isEqualTo(page);
    }

//    @Mock
//    private CourseRecommenderFun courseRecommenderFun;
//
//    @Autowired
//    private CourseRecommender underTest;
//
//
//    @Test
//    void recommendedCourses() {
//        int pageNumber = 1;
//        Page<Course> page = getCourses();
//        org.mockito.Mockito.when(courseRecommenderFun.recommendedCourses(pageNumber)).thenReturn(page);
//
//        underTest.recommendedCourses(pageNumber);
//
//        ArgumentCaptor<Integer> pageNumberArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
//        org.mockito.Mockito.verify(underTest).recommendedCourses(pageNumberArgumentCaptor.capture());
//        int queriedPageNumber = pageNumberArgumentCaptor.getValue();
//
//        assertThat(queriedPageNumber).isEqualTo(pageNumber);
//    }


    private static Page<Course> getCourses() {
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
        Page<Course> page = new PageImpl<>(courses);
        return page;
    }
}