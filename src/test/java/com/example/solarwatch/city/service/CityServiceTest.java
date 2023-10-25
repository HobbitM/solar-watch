package com.example.solarwatch.city.service;

import com.example.solarwatch.city.model.City;
import com.example.solarwatch.city.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

class CityServiceTest {
    private CityService cityService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CityRepository cityRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        cityService = new CityService(restTemplate, cityRepository);
    }

    @Test
    public void testAddCity() {
        String name = "TestCity";
        String country = "TestCountry";
        String state = "TestState";
        double lat = 1.0;
        double lon = 2.0;

        Mockito.when(cityRepository.save(any()))
                .thenReturn(new City(name, country, state, lat, lon));

        cityService.addCity(name, country, state, lat, lon);

        Mockito.verify(cityRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void testGetCityByName() {
        String cityName = "TestCity";
        City city = new City(cityName, "TestCountry", "TestState", 1.0, 2.0);

        Mockito.when(cityRepository.findByNameIgnoreCase(cityName))
                .thenReturn(city);

        City result = cityService.getCityByName(cityName);

        assertEquals(city, result);
    }

    @Test
    public void testGetCityFromOpenWeatherAPI() {
        String cityName = "TestCity";
        String apiKey = "YourAPIKey";
        City city = new City(cityName, "TestCountry", "TestState", 1.0, 2.0);

        Mockito.when(restTemplate.getForEntity(any(), any()))
                .thenReturn(new ResponseEntity<>(new City[]{city}, HttpStatus.OK));

        cityService.setApiKey(apiKey);

        City result = cityService.getCityFromOpenWeatherAPI(cityName);

        assertEquals(city, result);
    }
}