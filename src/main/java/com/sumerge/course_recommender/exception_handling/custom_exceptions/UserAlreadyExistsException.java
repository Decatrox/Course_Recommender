package com.sumerge.course_recommender.exception_handling.custom_exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String message){
        super((message));}
}
