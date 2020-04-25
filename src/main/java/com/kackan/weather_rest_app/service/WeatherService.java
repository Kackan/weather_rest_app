package com.kackan.weather_rest_app.service;
import com.kackan.weather_rest_app.model.ConsolidatedWeather;

import java.util.List;

public interface WeatherService {
    List<ConsolidatedWeather> getWeather(String location);
    String getLocation(String name);
    String getImage(String name);
}
