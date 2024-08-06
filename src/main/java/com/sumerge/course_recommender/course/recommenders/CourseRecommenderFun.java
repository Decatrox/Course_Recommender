package com.sumerge.course_recommender.course.recommenders;

import com.sumerge.course_recommender.course.Course;
import com.sumerge.course_recommender.course.CourseRecommender;
import com.sumerge.course_recommender.course.CourseRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Primary
@Component
public class CourseRecommenderFun implements CourseRecommender {
    private final CourseRepository courseRepository;

    public CourseRecommenderFun(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    @Override
    public Page<Course> recommendedCourses(int pageNumber) {
            return courseRepository.findAll(PageRequest.of(pageNumber, 2));
        }
}
