package com.spring.jpa.springbootjpasample;

import com.spring.jpa.springbootjpasample.dao.CustomerRepository;
import com.spring.jpa.springbootjpasample.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootJpaSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJpaSampleApplication.class, args);
    }
}
