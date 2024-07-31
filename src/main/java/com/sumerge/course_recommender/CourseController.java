package com.sumerge.course_recommender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


//ChatGPT Code just for testing
@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/recommend")
    public List<Course> recommendCourse() {
        return courseService.getRecommendedCourses();
    }
}
