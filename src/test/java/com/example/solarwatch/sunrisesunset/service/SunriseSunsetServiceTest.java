package com.example.solarwatch.sunrisesunset.service;

import com.example.solarwatch.city.model.City;
import com.example.solarwatch.sunrisesunset.model.Results;
import com.example.solarwatch.sunrisesunset.model.SunriseSunset;
import com.example.solarwatch.sunrisesunset.model.SunriseSunsetResponse;
import com.example.solarwatch.sunrisesunset.repository.SunriseSunsetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.mockito.ArgumentMatchers.eq;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;


class SunriseSunsetServiceTest {

    private SunriseSunsetService sunriseSunsetService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private SunriseSunsetRepository sunriseSunsetRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sunriseSunsetService = new SunriseSunsetService(restTemplate, sunriseSunsetRepository);
    }

    @Test
    public void testGetSunriseSunsetFromApi() {
        City city = new City("TestCity", "TestCountry", "TestState", 1.0, 2.0);
        LocalDate date = LocalDate.now();
        SunriseSunsetResponse sunriseSunsetResponse = new SunriseSunsetResponse();
        sunriseSunsetResponse.setResults(new Results("6:00 AM", "6:00 PM"));

        ResponseEntity<SunriseSunsetResponse> responseEntity = new ResponseEntity<>(sunriseSunsetResponse, HttpStatus.OK);

        Mockito.when(restTemplate.getForEntity(any(String.class), eq(SunriseSunsetResponse.class)))
                .thenReturn(responseEntity);

        SunriseSunset sunriseSunset = sunriseSunsetService.getSunriseSunsetFromApi(city, city.getLat(), city.getLon(), date);

        assertEquals("6:00 AM", sunriseSunset.getSunrise());
        assertEquals("6:00 PM", sunriseSunset.getSunset());
    }

    @Test
    public void testAddSunriseSunset() {
        City city = new City("TestCity", "TestCountry", "TestState", 1.0, 2.0);
        LocalDate date = LocalDate.now();
        String sunrise = "6:00 AM";
        String sunset = "6:00 PM";

        Mockito.when(sunriseSunsetRepository.save(any()))
                .thenReturn(new SunriseSunset(city, date, sunrise, sunset));

        sunriseSunsetService.addSunriseSunset(city, date, sunrise, sunset);

        Mockito.verify(sunriseSunsetRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void testGetSunriseSunset() {
        City city = new City("TestCity", "TestCountry", "TestState", 1.0, 2.0);
        LocalDate date = LocalDate.now();
        SunriseSunset sunriseSunset = new SunriseSunset(city, date, "6:00 AM", "6:00 PM");

        Mockito.when(sunriseSunsetRepository.findByCityAndDate(any(), any()))
                .thenReturn(java.util.Optional.of(sunriseSunset));

        SunriseSunset result = sunriseSunsetService.getSunriseSunset(city, date);

        assertEquals(sunriseSunset, result);
    }
}