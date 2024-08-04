package com.sumerge.course_recommender;


import com.sumerge.course_recommender.model.Course;

import java.util.List;

public interface CourseRecommender {
    List<Course> recommendedCourses();
}
