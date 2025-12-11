// WeatherService.java (Interface)
package com.healthpal.service;

public interface WeatherService {
    String getWeather(String city);
    String getAirQuality(String city);
    String getHealthRecommendations(String city);
}
