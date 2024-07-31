package com.sumerge.course_recommender;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Primary
@Component
public class CourseRecommenderFun implements CourseRecommender {
    @Override
    public List<Course_old> recommendedCourses() {
        List<Course_old> courses = new ArrayList<Course_old>();
        courses.add(new Course_old("HCI", "Fun Course, AR Project", 4, "Wael Abouelsaadat"));
        courses.add(new Course_old("Optimization", "Fun Algorithms", 4, "Omar Shehata"));
        return courses;

    }
}
