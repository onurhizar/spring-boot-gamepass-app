package com.onurhizar.gamepass.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleException(EntityNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }

    @ExceptionHandler(UnacceptableRequestException.class)
    public ResponseEntity<Object> handleException(UnacceptableRequestException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleException(BadCredentialsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleException(HttpMessageNotReadableException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
