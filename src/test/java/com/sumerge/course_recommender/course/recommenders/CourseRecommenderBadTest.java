package com.sumerge.course_recommender.course.recommenders;

import com.sumerge.course_recommender.course.Course;
import com.sumerge.course_recommender.mapper.MapStructMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseRecommenderBadTest {

    @Mock
    private WebServiceTemplate webServiceTemplate;
    @Mock
    private MapStructMapper mapStructMapper;
    @InjectMocks
    private CourseRecommenderBad courseRecommenderBad;

    @Test
    void whenGetRecommendedCoursesPage_callCourseRepositoryFindAll() {
        GetCoursesResponse getCoursesResponse = new GetCoursesResponse();
        GetCoursesResponse.Course course1 = new GetCoursesResponse.Course();
        course1.setName("Course 1");
        GetCoursesResponse.Course course2 = new GetCoursesResponse.Course();
        course2.setName("Course 2");
        GetCoursesResponse.Course course3 = new GetCoursesResponse.Course();
        course2.setName("Course 3");
        getCoursesResponse.getCourse().addAll(Arrays.asList(course1, course2, course3));

        Course mappedCourse1 = new Course();
        mappedCourse1.setName("Course 1");
        Course mappedCourse2 = new Course();
        mappedCourse2.setName("Course 2");
        Course mappedCourse3 = new Course();
        mappedCourse3.setName("Course 3");

        when(webServiceTemplate.marshalSendAndReceive(any(String.class), any(GetCoursesRequest.class), any()))
                .thenReturn(getCoursesResponse);
        when(mapStructMapper.courseRecommenderToCourse(course1)).thenReturn(mappedCourse1);
        when(mapStructMapper.courseRecommenderToCourse(course2)).thenReturn(mappedCourse2);
        when(mapStructMapper.courseRecommenderToCourse(course3)).thenReturn(mappedCourse3);

        Page<Course> result = courseRecommenderBad.getRecommendedCoursesPage(0);

        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getContent()).hasSize(3);
        assertThat(result.getContent().getFirst().getName()).isEqualTo("Course 1");
        assertThat(result.getContent().get(1).getName()).isEqualTo("Course 2");
        assertThat(result.getContent().getLast().getName()).isEqualTo("Course 3");

        verify(webServiceTemplate).marshalSendAndReceive(any(String.class), any(GetCoursesRequest.class), any());
        verify(mapStructMapper).courseRecommenderToCourse(course1);
        verify(mapStructMapper).courseRecommenderToCourse(course2);
        verify(mapStructMapper).courseRecommenderToCourse(course3);
    }
}