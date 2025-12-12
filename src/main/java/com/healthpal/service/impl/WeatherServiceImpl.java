package com.healthpal.service.impl;
import com.healthpal.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);
    private final RestTemplate restTemplate;

    @Override
    public String getWeather(String city) {
        try {
            // 1. Get Coordinates
            String geoUrl = "https://geocoding-api.open-meteo.com/v1/search?name=" + city + "&count=1&language=en&format=json";
            String geoBody = restTemplate.getForObject(geoUrl, String.class);

            if (geoBody == null || !geoBody.contains("\"latitude\":")) {
                return "{\"error\": \"City not found\"}";
            }

            // Extract lat/long manually (Quick & Dirty for Demo)
            String lat = extract(geoBody, "\"latitude\":");
            String lon = extract(geoBody, "\"longitude\":");

            // 2. Get Weather
            String weatherUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + lat + "&longitude=" + lon + "&current=temperature_2m,relative_humidity_2m,wind_speed_10m";
            return restTemplate.getForObject(weatherUrl, String.class);

        } catch (Exception e) {
            return "{\"error\": \"Weather service unavailable\", \"details\": \"" + e.getMessage() + "\"}";
        }
    }

    @Override
    public String getAirQuality(String city) {
        // Same logic, simplified for demo
        return "{\"aqi\": 50, \"status\": \"Good\", \"city\": \"" + city + "\"}"; // Mocked to save time if needed
    }

    @Override
    public String getHealthRecommendations(String city) {
        return "{\"recommendation\": \"Drink water and stay cool.\", \"city\": \"" + city + "\"}";
    }

    // Helper method to parse JSON string manually
    private String extract(String json, String key) {
        try {
            int start = json.indexOf(key) + key.length();
            int end = json.indexOf(",", start);
            if (end == -1) end = json.indexOf("}", start);
            return json.substring(start, end).trim();
        } catch (Exception e) {
            return "0";
        }
    }
}