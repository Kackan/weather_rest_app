package com.kackan.weather_rest_app.controller;

import com.kackan.weather_rest_app.model.ConsolidatedWeather;
import com.kackan.weather_rest_app.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class WeatherController {
    private WeatherService service;

    @Autowired
    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @GetMapping("/home")
    public String getHome()
    {
        return "index";
    }

    @PostMapping("/weather")
    public String getWeather(@RequestParam String location, Model model)
    {
        System.out.println(location);
        String s= service.getLocation(location);

        List<ConsolidatedWeather> weathers = service.getWeather(s);
        weathers.forEach(w-> System.out.println(w.toString()));

        model.addAttribute("weathers",weathers);
        return "weather";
    }
}
