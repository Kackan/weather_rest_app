package com.kackan.weather_rest_app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kackan.weather_rest_app.model.ConsolidatedWeather;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final static String URL_ADDRESS_LOCATION="https://www.metaweather.com/api/location/search/?query=";
    private final static String URL_ADDRESS_WEATHER="https://www.metaweather.com/api/location/";
    private final static String URL_ADDRESS_IMAGE="https://www.metaweather.com/static/img/weather/png/64/";
    private RestTemplate restTemplate;

    public WeatherServiceImpl() {
        restTemplate=new RestTemplate();
    }

    @Override
    public List<ConsolidatedWeather> getWeather(String location) {
        ConsolidatedWeather weathers = restTemplate.getForObject(URL_ADDRESS_WEATHER + location, ConsolidatedWeather.class);

        ArrayList<ConsolidatedWeather> appropriateWeathers=new ArrayList<>();

        Map<String, Object> additionalProperties = weathers != null ? weathers.getAdditionalProperties() : null;

        if(additionalProperties!=null) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNodes = mapper.convertValue(additionalProperties, JsonNode.class);
            JsonNode consol_weathers = jsonNodes.get("consolidated_weather");

        for (JsonNode node : consol_weathers) {
            ConsolidatedWeather weather1 = mapper.convertValue(node, ConsolidatedWeather.class);
            weather1.setImage(getImage(weather1.getWeatherStateAbbr()));
            appropriateWeathers.add(weather1);
        }

        }

        return appropriateWeathers;
    }

    @Override
    public String getLocation(String name) {
        JsonNode jn=restTemplate.getForObject(URL_ADDRESS_LOCATION + name, JsonNode.class);
        String location= jn != null ? jn.findValue("woeid").asText() : null;
        return location;
    }


    @Override
    public String getImage(String name)
    {
        return URL_ADDRESS_IMAGE+name+".png";
    }

}
