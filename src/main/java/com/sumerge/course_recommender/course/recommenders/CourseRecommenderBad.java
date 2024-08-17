package com.sumerge.course_recommender.course.recommenders;

import com.sumerge.course_recommender.course.CourseRecommender;
import com.sumerge.course_recommender.mapper.MapStructMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;

import java.util.List;

@Primary
@Component
@AllArgsConstructor
public class CourseRecommenderBad implements CourseRecommender {
    private final MapStructMapper mapStructMapper;
    private final WebServiceTemplate webServiceTemplate;


    @Override
    public Page<com.sumerge.course_recommender.course.Course> getRecommendedCoursesPage(int pageNumber) {
        GetCoursesRequest request = new GetCoursesRequest();

        GetCoursesResponse response = (GetCoursesResponse) webServiceTemplate.marshalSendAndReceive(
                "http://localhost:8088/courseSOAPServiceMock",
                request,
                message -> ((SoapMessage) message).setSoapAction("com/sumerge/course-wsdl/getCourses")
        );

        List<com.sumerge.course_recommender.course.Course> courses = response.getCourse().stream()
                .map(mapStructMapper::courseRecommenderToCourse)
                .toList();

        return new PageImpl<>(courses, PageRequest.of(pageNumber, 3), courses.size());
    }
}
