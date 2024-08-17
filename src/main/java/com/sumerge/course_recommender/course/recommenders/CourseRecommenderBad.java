package com.sumerge.course_recommender.course.recommenders;

import com.sumerge.course_recommender.course.CourseRecommender;
import com.sumerge.course_recommender.course.CourseRepository;
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
import java.util.stream.Collectors;

@Primary
@Component
@AllArgsConstructor
public class CourseRecommenderBad implements CourseRecommender {
    private final CourseRepository courseRepository;
    private final CourseService courseService;
    private final MapStructMapper mapStructMapper;
    private final WebServiceTemplate webServiceTemplate;


    @Override
    public Page<com.sumerge.course_recommender.course.Course> getRecommendedCoursesPage(int pageNumber) {
        GetCoursesRequest request = new GetCoursesRequest();
        request.setName("name");

        // Call the SOAP service to get the response
        GetCoursesResponse response = (GetCoursesResponse) webServiceTemplate.marshalSendAndReceive(
                "http://localhost:8088/courseSOAPServiceMock", // Replace with your service URL
                request,
                message -> ((SoapMessage) message).setSoapAction("com/sumerge/course-wsdl/getCourses")
        );

        List<com.sumerge.course_recommender.course.Course> courses = response.getCourse().stream()
                .map(mapStructMapper::courseRecommenderToCourse)
                .collect(Collectors.toList());

        int start = Math.min((int) PageRequest.of(pageNumber, 3).getOffset(), courses.size());
        int end = Math.min((start + 3), courses.size());
        List<com.sumerge.course_recommender.course.Course> pageContent = courses.subList(start, end);

        return new PageImpl<>(pageContent, PageRequest.of(pageNumber, 3), courses.size());
    }
}
