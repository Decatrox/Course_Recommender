package com.sumerge.course_recommender;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Component("badRecommender")
public class CourseRecommenderBad implements CourseRecommender {
    @Override
    public List<Course> recommendedCourses() {
        List<Course> courses = new ArrayList<Course>();
        courses.add(new Course("Circuits", "Seems fun but isn't", 4, "Someone"));
        courses.add(new Course("Signals", "Why?", 4, "Another One"));
        return courses;

    }
}
