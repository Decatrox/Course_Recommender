package com.sumerge.course_recommender.exception_handling;


import com.sumerge.course_recommender.exception_handling.custom_exceptions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private BindingResult bindingResult;

    GlobalExceptionHandler globalExceptionHandler;

    @Test
    void shouldHandleValidationExceptionsAndReturnErrorMessage() {
        globalExceptionHandler = new GlobalExceptionHandler();
        FieldError fieldError = new FieldError("Test Object Name (Course)", "Test Field Name (name)", "Test Message (name can't be null)");
        org.mockito.Mockito.when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));
        ArrayList<String> messages = new ArrayList<>();
        messages.add("Test Message (name can't be null)");

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
        ResponseEntity<Map<String, ArrayList<String>>> res = globalExceptionHandler.handleValidationExceptions(ex);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(res.getBody()).containsEntry("Test Field Name (name)", messages);
    }


    @Test
    void shouldHandleHttpMessageNotReadableExceptionAndReturnErrorMessage() {
        globalExceptionHandler = new GlobalExceptionHandler();
        ResponseEntity<String> res = globalExceptionHandler.handleHttpMessageNotReadableException(new HttpMessageNotReadableException(""));
        assertThat(res.getBody()).isNotEmpty();
    }

    @Test
    void shouldHandleAlreadyExistsExceptionAndReturnErrorMessage() {
        globalExceptionHandler = new GlobalExceptionHandler();
        //Author
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleAlreadyExistsException(new AuthorAlreadyExistsException(""));
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

        //Course
        response = globalExceptionHandler.handleAlreadyExistsException(new CourseAlreadyExistsException(""));
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

        //User
        response = globalExceptionHandler.handleAlreadyExistsException(new UserAlreadyExistsException(""));
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void shouldHandleNotFoundExceptionAndReturnErrorMessage() {
        globalExceptionHandler = new GlobalExceptionHandler();
        //Author
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleNotFoundException(new AuthorNotFoundException(""));
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        //Course
        response = globalExceptionHandler.handleNotFoundException(new CourseNotFoundException(""));
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        //Page
        response = globalExceptionHandler.handleNotFoundException(new PageNotFoundException(""));
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }
}