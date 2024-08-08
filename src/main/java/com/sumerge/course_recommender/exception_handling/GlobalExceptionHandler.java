package com.sumerge.course_recommender.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
//            ArrayList<String> errorList;
//            if (errors.containsKey(fieldName)) {
//                errorList = errors.get(fieldName);
//            }
//            else{
//                errorList = new ArrayList<>();
//            }
//            errorList.add(errorMessage);
//            errors.put(fieldName, errorList);
            errors.computeIfAbsent(fieldName, _ -> new ArrayList<>()).add(errorMessage);

        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}


