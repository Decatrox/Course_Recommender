package com.sumerge.course_recommender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service//@Component
public class CourseService {

    CourseRecommender courseRecommender;
    @Autowired
    private CourseRecommender courseRecommenderFun;

    @Autowired
    public CourseService(@Qualifier("courseRecommenderFun") CourseRecommender courseRecommender) {
        this.courseRecommender = courseRecommender;
    }

    @Autowired
    public void setCourseRecommender(CourseRecommender courseRecommender) {
        this.courseRecommender = courseRecommender;
    }

    List<Course> getRecommendedCourses() {
//        return courseRecommender.recommendedCourses();
        return courseRecommenderFun.recommendedCourses();
    }
}
