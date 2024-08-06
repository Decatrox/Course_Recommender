package com.sumerge.course_recommender.course;



import org.springframework.data.domain.Page;


public interface CourseRecommender {
    Page<Course> recommendedCourses(int pageNumber);
}
