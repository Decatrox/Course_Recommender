package com.sumerge.course_recommender.exception_handling.custom_exceptions;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String message) {
        super(message);
    }
}
