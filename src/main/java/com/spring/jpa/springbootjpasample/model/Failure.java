package com.spring.jpa.springbootjpasample.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Failure {
    private int errorCode;
    private String message;
    private String developerMessage;
}
