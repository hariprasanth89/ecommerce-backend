package org.example.ecommercebackend.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String home() {
        return "E-commerce Backend is running!";
    }

    @GetMapping("/test")
    public String test() {
        return "Test endpoint works!";
    }
}