package com.example.solarwatch.controller;

import com.example.solarwatch.city.model.City;
import com.example.solarwatch.city.service.CityService;
import com.example.solarwatch.sunrisesunset.model.SunriseSunset;
import com.example.solarwatch.sunrisesunset.service.SunriseSunsetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
public class SolarWatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityService cityService;

    @MockBean
    private SunriseSunsetService sunriseSunsetService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetSunriseSunsetForDate() throws Exception {
        String cityName = "TestCity";
        City mockCity = new City(cityName, "TestCountry", "TestState", 1.0, 2.0);
        LocalDate date = LocalDate.now();
        SunriseSunset mockSunriseSunset = new SunriseSunset(mockCity, date, "6:00 AM", "6:00 PM");

        Mockito.when(cityService.getCityByName(cityName)).thenReturn(mockCity);

        Mockito.when(sunriseSunsetService.getSunriseSunset(any(), any())).thenReturn(mockSunriseSunset);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/solar-watch")
                        .param("city", cityName))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();

        assertEquals(HttpStatus.OK.value(), status);

        assertEquals("6:00 AM", mockSunriseSunset.getSunrise());
    }
}