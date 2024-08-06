package com.sumerge.course_recommender.course;



import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseRecommender {
    Page<Course> recommendedCourses(int pageNumber);
}
