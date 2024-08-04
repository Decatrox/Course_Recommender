package com.sumerge.course_recommender;

import com.sumerge.course_recommender.model.Course;
import com.sumerge.course_recommender.repo.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Primary
@Component
public class CourseRecommenderFun implements CourseRecommender {
    private CourseRepository courseRepository;

    @Autowired
    public CourseRecommenderFun(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


        @Override
    public List<Course> recommendedCourses() {
            List<Course> courses= new ArrayList<>();
            courses.add(courseRepository.viewCourse(UUID.fromString("b8c55d01-22e4-4376-980f-99c76415dceb")));
            courses.add(courseRepository.viewCourse(UUID.fromString("c366efc1-6796-4f64-af2b-964960f16115")));
            return courses;
        }
}
