package com.spring.jpa.springbootjpasample.advice;

import com.spring.jpa.springbootjpasample.exception.CaughtCustomException;
import com.spring.jpa.springbootjpasample.exception.ResourceNotFoundException;
import com.spring.jpa.springbootjpasample.model.Failure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseBody
public class ProductControllerAdvice {

    @ExceptionHandler(CaughtCustomException.class)
    public ResponseEntity<Failure> handleCaughtException(CaughtCustomException caughtCustomException) {
        Failure failure = new Failure(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server was unable to handler request", caughtCustomException.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(failure);
    }


    // Try to comment out below to see default ResourceNotFoundException code and reason.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Failure> handleResourceNotException(ResourceNotFoundException resourceNotFoundException){
        Failure failure = new Failure(HttpStatus.NOT_FOUND.value(), "Resource was not found on server side", resourceNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(failure);
    }
}
