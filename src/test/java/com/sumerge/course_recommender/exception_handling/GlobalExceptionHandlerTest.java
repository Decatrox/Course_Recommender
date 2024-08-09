package com.sumerge.course_recommender.exception_handling;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
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

//    @InjectMocks
    GlobalExceptionHandler underTest;

    @Test
    void shouldHandleValidationExceptionsAndReturnErrorMessage() {
        underTest = new GlobalExceptionHandler();
        FieldError fieldError = new FieldError("Test Object Name (Course)", "Test Field Name (name)", "Test Message (name can't be null)");
        org.mockito.Mockito.when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));
        ArrayList<String> messages = new ArrayList<>();
        messages.add("Test Message (name can't be null)");

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
        ResponseEntity<Map<String, ArrayList<String>>> res = underTest.handleValidationExceptions(ex);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(res.getBody().get("Test Field Name (name)")).isEqualTo((messages));
    }

    @Test
    void shouldHandleEntityNotFoundExceptionAndReturnErrorMessage() {
        underTest = new GlobalExceptionHandler();
        ResponseEntity<String> s = underTest.handleEntityNotFoundException(new EntityNotFoundException());
        assertThat(s.getBody()).isNotEmpty();
        assertThat(s.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldHandleHttpMessageNotReadableExceptionAndReturnErrorMessage() {
        underTest = new GlobalExceptionHandler();
        ResponseEntity<String> res = underTest.handleHttpMessageNotReadableException(new HttpMessageNotReadableException(""));
        assertThat(res.getBody()).isNotEmpty();
    }

    @Test
    void shouldHandleEntityAlreadyExistsExceptionAndReturnErrorMessage() {
        underTest = new GlobalExceptionHandler();
        ResponseEntity<String> s = underTest.handleEntityExistsException(new EntityExistsException());
        assertThat(s.getBody()).isNotEmpty();
        assertThat(s.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }
}