package com.sumerge.course_recommender;

import com.sumerge.course_recommender.model.Course;
import com.sumerge.course_recommender.repo.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CourseRecommenderBad implements CourseRecommender {
    private CourseRepository courseRepository;

    @Autowired
    public CourseRecommenderBad(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> recommendedCourses() {
        List<Course> courses= new ArrayList<>();
        courses.add(courseRepository.viewCourse(UUID.fromString("8e74f445-a7e5-43ea-876f-c9d7584bd066")));
        courses.add(courseRepository.viewCourse(UUID.fromString("0d663a17-c4c1-4111-b7c4-e7c53c6adf5d")));
        return courses;
    }

}
