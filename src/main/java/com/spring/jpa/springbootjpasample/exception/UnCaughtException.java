package com.spring.jpa.springbootjpasample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Below class is uncaught and not formatted by any controller advice. same as ResourceNotFound
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnCaughtException extends RuntimeException {
    public UnCaughtException(String message) {
        super(message);
    }
}