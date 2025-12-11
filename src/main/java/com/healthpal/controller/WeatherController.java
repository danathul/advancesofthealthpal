package com.healthpal.controller;

import com.healthpal.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "External API Integration - Weather & Air Quality")
@RestController
@RequestMapping("/api/v1/external")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @Operation(summary = "Get current weather for a city")
    @GetMapping("/weather/{city}")
    public ResponseEntity<String> getWeather(@PathVariable String city) {
        return ResponseEntity.ok(weatherService.getWeather(city));
    }

    @Operation(summary = "Get air quality index for a city")
    @GetMapping("/air-quality/{city}")
    public ResponseEntity<String> getAirQuality(@PathVariable String city) {
        return ResponseEntity.ok(weatherService.getAirQuality(city));
    }

    @Operation(summary = "Get health recommendations based on weather")
    @GetMapping("/health-recommendations/{city}")
    public ResponseEntity<String> getHealthRecommendations(@PathVariable String city) {
        return ResponseEntity.ok(weatherService.getHealthRecommendations(city));
    }
}