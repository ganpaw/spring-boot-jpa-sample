package com.spring.jpa.springbootjpasample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Below class is further handled /formatted by ProductControllerAdvice (@RestControllerAdvice)
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CaughtCustomException extends RuntimeException {
    public CaughtCustomException(String message) {
        super(message);
    }
}