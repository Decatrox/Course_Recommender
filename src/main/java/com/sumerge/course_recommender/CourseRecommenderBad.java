package com.sumerge.course_recommender;

import java.util.ArrayList;
import java.util.List;

public class CourseRecommenderBad implements CourseRecommender {
    @Override
    public List<Course_old> recommendedCourses() {
        List<Course_old> courses = new ArrayList<Course_old>();
        courses.add(new Course_old("Circuits", "Seems fun but isn't", 4, "Someone"));
        courses.add(new Course_old("Signals", "Why?", 4, "Another One"));
        return courses;

    }
}
