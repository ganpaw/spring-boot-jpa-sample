package com.spring.jpa.springbootjpasample.controller;

import com.spring.jpa.springbootjpasample.exception.CaughtCustomException;
import com.spring.jpa.springbootjpasample.exception.UnCaughtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/test/errors")
public class SampleController {

    @GetMapping("/unavailable")
    public ResponseEntity<HttpStatus> unavailableTest() {
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping("/rse")
    public String withResponseStatusException() {
        try {
            throw new RuntimeException("Error Occurred");
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "HTTP Status will be NOT FOUND (CODE 404)");
        }
    }

    @GetMapping("/caught")
    public String caughtException() {
        throw new CaughtCustomException("Caught Exception Thrown");
    }

    @GetMapping("/uncaught")
    public String unCaughtException() {
        throw new UnCaughtException("The HTTP Status will be BAD REQUEST (CODE 400)");
    }
}
