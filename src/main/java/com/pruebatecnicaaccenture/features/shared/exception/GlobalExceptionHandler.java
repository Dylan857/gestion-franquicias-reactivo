package com.pruebatecnicaaccenture.features.shared.exception;

import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.pruebatecnicaaccenture.features.shared.response.ApiResponse;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ApiResponse<Object>>> handleException(Exception exception) {
        int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ApiResponse<Object> response = new ApiResponse<>(
                statusCode,
                "Internal server error",
                "0000",
                exception.getMessage());

        return Mono.just(ResponseEntity.status(statusCode).body(response));
    }

    @ExceptionHandler(CustomException.class)
    public Mono<ResponseEntity<ApiResponse<Object>>> handleCustomException(CustomException ex) {
        ApiResponse<Object> response = new ApiResponse<>(
                ex.getStatusCode(),
                ex.getMessage(),
                ex.getErrorCode(),
                ex.getSeverity());

        return Mono.just(ResponseEntity.status(ex.getStatusCode()).body(response));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ApiResponse<Object>>> handleValidationExceptions(WebExchangeBindException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                message,
                "0000",
                "error");
        return Mono.just(ResponseEntity.badRequest().body(response));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public Mono<ResponseEntity<ApiResponse<Object>>> handleConflict(DataIntegrityViolationException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(400, "El registro ya existe", "0000", "error")));
    }
}