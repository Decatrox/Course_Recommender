package com.sumerge.course_recommender.exception_handling.custom_exceptions;

public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException(String message) {
        super(message);
    }
}
