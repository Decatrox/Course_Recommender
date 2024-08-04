package com.sumerge.course_recommender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service//@Component
public class CourseService {
    private CourseRecommender courseRecommender;
    //need just 1 autowired. this is just for the task.

//    @Autowired
//    public CourseService(@Qualifier("badRecommender") CourseRecommender courseRecommender) {
    public CourseService(CourseRecommender courseRecommender) {
        this.courseRecommender = courseRecommender;
    }

    public CourseService() {
        System.out.println("In constructor");
    }

//    @Autowired
//    @Qualifier("badRecommender")
//    public void setCourseRecommender(CourseRecommender courseRecommender) {
//        this.courseRecommender = courseRecommender;
//    }

    List<Course> getRecommendedCourses() {
//        return courseRecommender.recommendedCourses();
        return courseRecommender.recommendedCourses();
    }
}
