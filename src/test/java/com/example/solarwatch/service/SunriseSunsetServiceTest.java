package com.example.solarwatch.service;

import com.example.solarwatch.model.SunriseSunsetReport;
import com.example.solarwatch.model.SunriseSunsetResults;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class SunriseSunsetServiceTest {

    @Autowired
    private SunriseSunsetService sunriseSunsetService;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void testGetSunriseSunsetWithDate() {
        double lat = 10.0;
        double lon = 20.0;
        LocalDate date = LocalDate.of(2023, 10, 4);
        SunriseSunsetReport mockReport = new SunriseSunsetReport(new SunriseSunsetResults("6:00:00 AM", "6:00:00 PM"));

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(SunriseSunsetReport.class)))
                .thenReturn(mockReport);

        SunriseSunsetReport result = sunriseSunsetService.getSunriseSunset(lat, lon, date);

        assertNotNull(result);
        assertEquals("6:00:00 AM", result.results().sunrise());
        assertEquals("6:00:00 PM", result.results().sunset());
    }

    @Test
    public void testGetSunriseSunsetWithoutDate() {
        double lat = 10.0;
        double lon = 20.0;
        SunriseSunsetReport mockReport = new SunriseSunsetReport(new SunriseSunsetResults("6:00:00 AM", "6:00:00 PM"));

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(SunriseSunsetReport.class)))
                .thenReturn(mockReport);

        SunriseSunsetReport result = sunriseSunsetService.getSunriseSunset(lat, lon);

        assertNotNull(result);
        assertEquals("6:00:00 AM", result.results().sunrise());
        assertEquals("6:00:00 PM", result.results().sunset());
    }
}