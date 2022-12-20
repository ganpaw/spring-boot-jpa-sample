package com.spring.jpa.springbootjpasample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Below class is uncaught and not formatted by any controller advice. same as ResourceNotFound
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Uncaught exception message")
public class UnCaughtException extends RuntimeException {
    public UnCaughtException(String message) {
        super(message);
    }
}