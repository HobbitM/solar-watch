package com.example.solarwatch.sunrisesunset.service;

import com.example.solarwatch.city.model.City;
import com.example.solarwatch.sunrisesunset.model.SunriseSunset;
import com.example.solarwatch.sunrisesunset.repository.SunriseSunsetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SunriseSunsetServiceIntegrationTest {

    @Autowired
    private SunriseSunsetService sunriseSunsetService;

    @MockBean
    private SunriseSunsetRepository sunriseSunsetRepository;

    @BeforeEach
    public void setUp() {
        LocalDate currentDate = LocalDate.of(2023, 11, 30);
        SunriseSunsetService sunriseSunsetServiceSpy = Mockito.spy(sunriseSunsetService);
        Mockito.doReturn(currentDate).when(sunriseSunsetServiceSpy).getCurrentDate();
        sunriseSunsetService = sunriseSunsetServiceSpy;
    }

    @Test
    public void shouldRetrieveSunriseSunsetFromApiAndSaveToRepository() {
        when(sunriseSunsetRepository.save(any(SunriseSunset.class))).thenReturn(new SunriseSunset());

        when(sunriseSunsetService.getSunriseSunsetFromApi(any(City.class), anyDouble(), anyDouble(), any(LocalDate.class)))
                .thenReturn(new SunriseSunset(new City(), LocalDate.of(2023, 11, 30), "06:00:00", "18:00:00"));

        City city = new City();

        SunriseSunset result = sunriseSunsetService.getSunriseSunsetFromApi(city, 0.0, 0.0, sunriseSunsetService.getCurrentDate());

        assertNotNull(result);
    }
}