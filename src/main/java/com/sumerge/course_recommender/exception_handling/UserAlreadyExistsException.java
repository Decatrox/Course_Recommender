package com.sumerge.course_recommender.exception_handling;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String message){
        super((message));}
}
