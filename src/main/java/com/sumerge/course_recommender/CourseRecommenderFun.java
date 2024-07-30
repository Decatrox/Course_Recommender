package com.sumerge.course_recommender;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Primary
@Component
public class CourseRecommenderFun implements CourseRecommender {
    @Override
    public List<Course> recommendedCourses() {
        List<Course> courses = new ArrayList<Course>();
        courses.add(new Course("HCI", "Fun Course, AR Project", 4, "Wael Abouelsaadat"));
        courses.add(new Course("Optimization", "Fun Algorithms", 4, "Omar Shehata"));
        return courses;

    }
}
