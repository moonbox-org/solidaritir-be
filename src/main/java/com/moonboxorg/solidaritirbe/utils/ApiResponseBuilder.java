package com.moonboxorg.solidaritirbe.utils;

import com.moonboxorg.solidaritirbe.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import static com.moonboxorg.solidaritirbe.exceptions.GlobalExceptionHandler.UNEXPECTED_ERROR_MSG;
import static org.springframework.http.HttpStatus.*;

@Slf4j
public class ApiResponseBuilder {

    private ApiResponseBuilder() {
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        log.info("Building success response with data: {}", data);
        return ResponseEntity.ok(new ApiResponse<>(OK.value(), OK.getReasonPhrase(), data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        log.info("Building created response with data: {}", data);
        return new ResponseEntity<>(new ApiResponse<>(CREATED.value(), CREATED.getReasonPhrase(), data), CREATED);
    }

    public static <T> ResponseEntity<ApiResponse<T>> deleted(T data) {
        log.info("Building deleted response with data: {}", data);
        return new ResponseEntity<>(new ApiResponse<>(NO_CONTENT.value(), NO_CONTENT.getReasonPhrase(), data), OK);
    }

    public static <T> ResponseEntity<ApiResponse<T>> notFound(String msg) {
        log.info("Building not found response");
        return new ResponseEntity<>(new ApiResponse<>(NOT_FOUND.value(), msg != null ? msg : NOT_FOUND.getReasonPhrase(), null), NOT_FOUND);
    }

    public static <T> ResponseEntity<ApiResponse<T>> noContent() {
        log.info("Building no content response");
        return new ResponseEntity<>(new ApiResponse<>(NO_CONTENT.value(), NO_CONTENT.getReasonPhrase(), null), OK);
    }

    public static <T> ResponseEntity<ApiResponse<T>> badRequest(String msg) {
        log.info("Building bad request response");
        return new ResponseEntity<>(new ApiResponse<>(BAD_REQUEST.value(), msg != null ? msg : BAD_REQUEST.getReasonPhrase(), null), BAD_REQUEST);
    }

    public static <T> ResponseEntity<ApiResponse<T>> serverError() {
        log.info("Building internal server error response");
        return new ResponseEntity<>(new ApiResponse<>(INTERNAL_SERVER_ERROR.value(), UNEXPECTED_ERROR_MSG, null), INTERNAL_SERVER_ERROR);
    }
}
