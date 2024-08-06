package com.sumerge.course_recommender.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(jakarta.validation.ConstraintViolationException ex, WebRequest request) {
        ApiError apiError = new ApiError(
                LocalDateTime.now()
//                HttpStatus.BAD_REQUEST.value(),
//                "Bad Request",
//                ex.getConstraintViolations().iterator().next().getMessage(),
//                request.getDescription(false)
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.springframework.transaction.TransactionSystemException.class)
    public ResponseEntity<ApiError> handleTransactionSystemException(org.springframework.transaction.TransactionSystemException ex, WebRequest request) {
        Throwable cause = ex.getRootCause();
        String message = (cause instanceof jakarta.validation.ConstraintViolationException) ?
                ((jakarta.validation.ConstraintViolationException) cause).getConstraintViolations().iterator().next().getMessage() :
                ex.getMessage();

        ApiError apiError = new ApiError(
                LocalDateTime.now()
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                "Internal Server Error",
//                message,
//                request.getDescription(false)
        );
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // You can add more exception handlers for other types of exceptions
}

