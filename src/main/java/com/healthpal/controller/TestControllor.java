package com.healthpal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestControllor {

    @GetMapping("/test")
    public String testConnection() {
        return "✅ HealthPal backend is running!";
    }
}
