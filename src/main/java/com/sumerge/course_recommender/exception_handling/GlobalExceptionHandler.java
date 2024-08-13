package com.sumerge.course_recommender.exception_handling;

import com.sumerge.course_recommender.exception_handling.custom_exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, ArrayList<String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, ArrayList<String>> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.computeIfAbsent(fieldName, nothing -> new ArrayList<>()).add(errorMessage);

        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({
            AuthorNotFoundException.class,
            CourseNotFoundException.class,
            PageNotFoundException.class
    })
    public ResponseEntity<Map<String, String>> handleNotFoundException(RuntimeException ex) {
        return buildErrorResponse("Not Found", ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({
            AuthorAlreadyExistsException.class,
            CourseAlreadyExistsException.class,
            UserAlreadyExistsException.class,

    })
    public ResponseEntity<Map<String, String>> handleAlreadyExistsException(RuntimeException ex) {
        return buildErrorResponse("Conflict", ex.getMessage(), HttpStatus.CONFLICT);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String errorMessage = "Invalid JSON format: " + ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }


    private ResponseEntity<Map<String, String>> buildErrorResponse(String error, String message, HttpStatus status) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", error);
        errorResponse.put("message", message);
        return new ResponseEntity<>(errorResponse, status);
    }
}


