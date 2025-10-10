package com.healthpal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "🏥 Welcome to HealthPal Backend — it's working!";
    }

    @GetMapping("/hello")
    public String hello() {
        return "✅ Hello from HealthPal backend!";
    }
}
