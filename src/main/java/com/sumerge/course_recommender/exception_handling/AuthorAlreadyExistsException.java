package com.sumerge.course_recommender.exception_handling;

public class AuthorAlreadyExistsException extends RuntimeException {
    public AuthorAlreadyExistsException(String message) {
        super(message);
    }
}

