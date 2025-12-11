
// WeatherServiceImpl.java
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

    // Using Open-Meteo API (free, no API key required)
    private static final String GEOCODING_URL = "https://geocoding-api.open-meteo.com/v1/search?name=%s&count=1&language=en&format=json";
    private static final String WEATHER_URL = "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&current=temperature_2m,relative_humidity_2m,wind_speed_10m&timezone=auto";
    private static final String AIR_QUALITY_URL = "https://air-quality-api.open-meteo.com/v1/air-quality?latitude=%s&longitude=%s&current=pm10,pm2_5&timezone=auto";

    @Override
    public String getWeather(String city) {
        try {
            logger.info("Fetching weather for city: {}", city);

            // Step 1: Get coordinates
            String geoUrl = String.format(GEOCODING_URL, city);
            ResponseEntity<String> geoResponse = restTemplate.getForEntity(geoUrl, String.class);
            String geoBody = geoResponse.getBody();

            if (geoBody == null || !geoBody.contains("latitude")) {
                return String.format("{\"error\":\"City '%s' not found\"}", city);
            }

            // Extract lat/lon (simple parsing)
            String lat = extractValue(geoBody, "\"latitude\":");
            String lon = extractValue(geoBody, "\"longitude\":");

            // Step 2: Get weather
            String weatherUrl = String.format(WEATHER_URL, lat, lon);
            ResponseEntity<String> weatherResponse = restTemplate.getForEntity(weatherUrl, String.class);

            logger.info("Weather fetched successfully for: {}", city);
            return weatherResponse.getBody();

        } catch (Exception e) {
            logger.error("Failed to fetch weather for {}: {}", city, e.getMessage());
            return String.format("{\"error\":\"Failed to fetch weather\",\"message\":\"%s\"}", e.getMessage());
        }
    }

    @Override
    public String getAirQuality(String city) {
        try {
            logger.info("Fetching air quality for city: {}", city);

            // Get coordinates
            String geoUrl = String.format(GEOCODING_URL, city);
            ResponseEntity<String> geoResponse = restTemplate.getForEntity(geoUrl, String.class);
            String geoBody = geoResponse.getBody();

            if (geoBody == null || !geoBody.contains("latitude")) {
                return String.format("{\"error\":\"City '%s' not found\"}", city);
            }

            String lat = extractValue(geoBody, "\"latitude\":");
            String lon = extractValue(geoBody, "\"longitude\":");

            // Get air quality
            String aqUrl = String.format(AIR_QUALITY_URL, lat, lon);
            ResponseEntity<String> aqResponse = restTemplate.getForEntity(aqUrl, String.class);

            logger.info("Air quality fetched successfully for: {}", city);
            return aqResponse.getBody();

        } catch (Exception e) {
            logger.error("Failed to fetch air quality for {}: {}", city, e.getMessage());
            return String.format("{\"error\":\"Failed to fetch air quality\",\"message\":\"%s\"}", e.getMessage());
        }
    }

    @Override
    public String getHealthRecommendations(String city) {
        try {
            String weather = getWeather(city);

            if (weather.contains("error")) {
                return weather;
            }

            // Extract temperature
            String tempStr = extractValue(weather, "\"temperature_2m\":");
            double temp = Double.parseDouble(tempStr);

            // Generate recommendations
            StringBuilder recommendations = new StringBuilder();
            recommendations.append("{\"city\":\"").append(city).append("\",");
            recommendations.append("\"temperature\":").append(temp).append(",");
            recommendations.append("\"recommendations\":[");

            if (temp > 35) {
                recommendations.append("\"Stay hydrated - drink plenty of water\",");
                recommendations.append("\"Avoid outdoor activities during peak heat\",");
                recommendations.append("\"Watch for heat exhaustion symptoms\"");
            } else if (temp > 30) {
                recommendations.append("\"Drink water regularly\",");
                recommendations.append("\"Limit strenuous outdoor activity\"");
            } else if (temp < 10) {
                recommendations.append("\"Dress warmly in layers\",");
                recommendations.append("\"Protect against cold-related illnesses\",");
                recommendations.append("\"Check on elderly neighbors\"");
            } else {
                recommendations.append("\"Weather conditions are moderate\",");
                recommendations.append("\"Good conditions for outdoor activities\"");
            }

            recommendations.append("]}");

            logger.info("Health recommendations generated for: {}", city);
            return recommendations.toString();

        } catch (Exception e) {
            logger.error("Failed to generate recommendations for {}: {}", city, e.getMessage());
            return String.format("{\"error\":\"Failed to generate recommendations\",\"message\":\"%s\"}", e.getMessage());
        }
    }

    private String extractValue(String json, String key) {
        int start = json.indexOf(key) + key.length();
        int end = json.indexOf(",", start);
        if (end == -1) end = json.indexOf("}", start);
        return json.substring(start, end).trim();
    }
}