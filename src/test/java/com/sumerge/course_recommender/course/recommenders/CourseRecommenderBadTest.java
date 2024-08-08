package com.sumerge.course_recommender.course.recommenders;

import com.sumerge.course_recommender.course.Course;
import com.sumerge.course_recommender.course.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseRecommenderBadTest {

    private CourseRecommenderBad underTest;

    @Mock
    private CourseRepository courseRepository;

    @Test
    void getsRecommendedCoursesBad() {
        underTest = new CourseRecommenderBad(courseRepository);
        int pageNumber = 1;
        Page<Course> page = getCourses(pageNumber);
        Pageable pageable = PageRequest.of(pageNumber, 3);
        when(courseRepository.findAll(pageable)).thenReturn(page);

        Page<Course> returnedPage = underTest.recommendedCourses(pageNumber);

        assertThat(returnedPage).isEqualTo(page);

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
        Pageable pageable = PageRequest.of(pageNumber, 3);
        Page<Course> page = new PageImpl<>(courses, pageable, courses.size());
        return page;
    }
}