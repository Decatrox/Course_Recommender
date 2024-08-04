package com.sumerge.course_recommender;

import com.sumerge.CourseRecommenderBad;

import java.util.ArrayList;
import java.util.List;

public class CourseRecommenderBad2 extends CourseRecommenderBad {
    @Override
            public List<Course> recommendedCourses(){
                List<Course> courses = new ArrayList<Course>();
                courses.add(new Course("Circuits 2 (Overridden test)", "Seems fun but isn't", 4, "Someone"));
                courses.add(new Course("Signals", "Why?", 4, "Another One"));
                return courses;
            }
}
