package com.example.solarwatch.city.service;

import com.example.solarwatch.city.model.City;
import com.example.solarwatch.city.repository.CityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CityService {

    @Value("${openweather.api.key}")
    private String API_KEY;

    private final RestTemplate restTemplate;
    private final CityRepository cityRepository;

    public CityService(RestTemplate restTemplate, CityRepository cityRepository) {
        this.restTemplate = restTemplate;
        this.cityRepository = cityRepository;
    }

    public void addCity(String name, String country, String state, double lat, double lon){
        cityRepository.save(new City(name, country, state, lat, lon));
    }

    public City getCityByName(String name){
        return cityRepository.findByNameIgnoreCase(name);
    }

    public City getCityFromOpenWeatherAPI(String city) {
        try {
            String url = String.format("https://api.openweathermap.org/geo/1.0/direct?q=%s&&appid=%s", city, API_KEY);

            ResponseEntity<City[]> responseEntity = restTemplate.getForEntity(url, City[].class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                City[] responseArray = responseEntity.getBody();
                if (responseArray != null && responseArray.length > 0) {
                    return responseArray[0];
                } else {
                    throw new RuntimeException("Empty or null response from Open Weather API.");
                }
            } else {
                throw new RuntimeException("Error response from Open Weather API: " + responseEntity.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while retrieving city information: " + e.getMessage());
        }
    }

    public void setApiKey(String apiKey) {
    }
}
