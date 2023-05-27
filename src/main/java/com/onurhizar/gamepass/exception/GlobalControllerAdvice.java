package com.onurhizar.gamepass.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleException(EntityNotFoundException e, HttpServletRequest request) {
        logRequesterDetails(request);
        return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }

    @ExceptionHandler(UnacceptableRequestException.class)
    public ResponseEntity<Object> handleException(UnacceptableRequestException e, HttpServletRequest request) {
        logRequesterDetails(request);
        return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleException(BadCredentialsException e, HttpServletRequest request) {
        logRequesterDetails(request);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleException(HttpMessageNotReadableException e, HttpServletRequest request) {
        logRequesterDetails(request);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException e, HttpServletRequest request) {
        logRequesterDetails(request);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e, HttpServletRequest request) {
        log.error("Unhandled exception occurred", e);
        logRequesterDetails(request);
        log.info("Internal Server Error: " + e.getMessage());
        return new ResponseEntity<>("internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void logRequesterDetails(HttpServletRequest request){
        if (request == null) return;
        log.error("Request URI: " + request.getRequestURI());
        log.error("Request Method: " + request.getMethod());
        log.error("Request Query String: " + request.getQueryString());
        log.error("Request Content Type: " + request.getContentType());
        log.error("Request User Agent Header: " + request.getHeader("User-Agent"));
        log.error("Request IP: " + request.getRemoteAddr());
    }
}
