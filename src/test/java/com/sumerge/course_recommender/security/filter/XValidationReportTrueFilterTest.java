package com.sumerge.course_recommender.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class XValidationReportTrueFilterTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private XValidationReportTrueFilter filter;


    @Test
    void doFilterInternalWhenXValidationReportTrue() throws ServletException, IOException {
        when(request.getHeader("x-validation-report")).thenReturn("true");

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(response);
    }

    @Test
    void FailDoFilterInternalWhenXValidationReport() throws ServletException, IOException {
        when(request.getHeader("x-validation-report")).thenReturn("false");

        filter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}