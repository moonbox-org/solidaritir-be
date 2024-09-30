package com.moonboxorg.solidaritirbe.exceptions;

import com.moonboxorg.solidaritirbe.dto.ApiResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String UNEXPECTED_ERROR_MSG = "An unexpected error occurred";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        ApiResponse<Object> response = new ApiResponse<>(NOT_FOUND.value(), e.getMessage(), null);
        return new ResponseEntity<>(response, NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(ValidationException e, WebRequest request) {
        ApiResponse<Object> response = new ApiResponse<>(BAD_REQUEST.value(), e.getMessage(), null);
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolationException(ConstraintViolationException e, WebRequest request) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        ApiResponse<Object> response = new ApiResponse<>(BAD_REQUEST.value(), errorMessage, null);
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    // Handle other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGlobalException(Exception ex, WebRequest request) {
        ApiResponse<Object> response = new ApiResponse<>(INTERNAL_SERVER_ERROR.value(), UNEXPECTED_ERROR_MSG, null);
        return new ResponseEntity<>(response, INTERNAL_SERVER_ERROR);
    }
}
