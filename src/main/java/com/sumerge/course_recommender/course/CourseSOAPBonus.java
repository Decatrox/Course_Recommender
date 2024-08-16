package com.sumerge.course_recommender.course;

import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
public class CourseSOAPBonus {
    private final WebServiceTemplate webServiceTemplate;

    public CourseSOAPBonus(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public Course getCourse(String courseId) {
        GetCourseRequest request = new GetCourseRequest();
//        request.setCourseId(courseId);

//        GetCourseResponse response = (GetCourseResponse) webServiceTemplate.marshalSendAndReceive(
//                "http://localhost:8080/mockCourseService", request);
        GetCourseResponse response = (GetCourseResponse) webServiceTemplate.marshalSendAndReceive(
                "http://Omars-MacBook-Pro.local:8088/mockCourseServiceBinding", request);

        return response.getCourse();
    }
}
