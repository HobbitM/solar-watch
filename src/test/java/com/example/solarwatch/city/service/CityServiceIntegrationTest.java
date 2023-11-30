package com.example.solarwatch.city.service;

import com.example.solarwatch.city.model.City;
import com.example.solarwatch.city.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CityServiceIntegrationTest {

    @Autowired
    private CityService cityService;

    @MockBean
    private CityRepository cityRepository;

    @Test
    public void shouldGetCityInformationFromOpenWeatherAPI() {

        when(cityRepository.save(any(City.class))).thenReturn(new City());

        City result = cityService.getCityFromOpenWeatherAPI("London");

        assertNotNull(result);
    }
}