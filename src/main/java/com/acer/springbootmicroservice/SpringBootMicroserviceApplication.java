package com.acer.springbootmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMicroserviceApplication.class, args);
    }

    @RequestMapping(value = "/")
    public String hello() {
        return "Hello Spring Boot";
    }
}