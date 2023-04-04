package com.onurhizar.gamepass.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class UnacceptableRequestException extends RuntimeException {

    private final HttpStatus statusCode = HttpStatus.BAD_REQUEST;
    private String message = "request is not acceptable"; // default message

    public UnacceptableRequestException(String message){
        this.message = message;
    }

}
