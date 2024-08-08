package com.sumerge.course_recommender.course.recommenders;

import com.sumerge.course_recommender.course.Course;
import com.sumerge.course_recommender.course.CourseRecommender;
import com.sumerge.course_recommender.course.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CourseRecommenderBad implements CourseRecommender {
    private final CourseRepository courseRepository;

    @Override
    public Page<Course> recommendedCourses(int pageNumber) {
        return courseRepository.findAll(PageRequest.of(pageNumber, 3));
    }
}
