package com.barclaysbanking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException ex, WebRequest request) {
        return buildErrorResponse(
                ex.getMessage(),
                HttpStatus.valueOf(ex.getStatus()),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleEmailAlreadyExists(EmailAlreadyExistsException ex, WebRequest request) {
        return buildErrorResponse(
                ex.getMessage(),
                HttpStatus.CONFLICT,
                request.getDescription(false)
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        ex.printStackTrace(); // 
        return buildErrorResponse(
        		"Something went wrong. Please try again later.",
                HttpStatus.INTERNAL_SERVER_ERROR,
                request.getDescription(false)
        );
    }

    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status, String path) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", path.replace("uri=", ""));
        return new ResponseEntity<>(body, status);
    }
}

