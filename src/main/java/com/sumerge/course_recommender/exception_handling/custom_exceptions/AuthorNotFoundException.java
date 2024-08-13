package com.sumerge.course_recommender.exception_handling.custom_exceptions;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(String message) {
        super(message);
    }
}


