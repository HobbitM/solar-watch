package com.example.solarwatch.controller;

import com.example.solarwatch.model.LatAndLonReport;
import com.example.solarwatch.model.SunriseSunsetReport;
import com.example.solarwatch.model.SunriseSunsetResults;
import com.example.solarwatch.service.LatAndLonService;
import com.example.solarwatch.service.SunriseSunsetService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SolarWatchController.class)
class SolarWatchControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LatAndLonService latAndLonService;

    @MockBean
    private SunriseSunsetService sunriseSunsetService;

    @Test
    public void testGetSunriseSunsetForDate() throws Exception {
        double lat = 10.0;
        double lon = 20.0;
        LocalDate date = LocalDate.of(2023, 10, 4);
        SunriseSunsetReport mockReport = new SunriseSunsetReport(new SunriseSunsetResults("6:00:00 AM", "6:00:00 PM"));

        Mockito.when(latAndLonService.getLatAndLonFromCity(Mockito.anyString())).thenReturn(new LatAndLonReport(lat, lon));
        Mockito.when(sunriseSunsetService.getSunriseSunset(lat, lon, date)).thenReturn(mockReport);

        mockMvc.perform(get("/solar-watch")
                        .param("date", "2023-10-04")
                        .param("city", "SampleCity")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results.sunrise").value("6:00:00 AM"))
                .andExpect(jsonPath("$.results.sunset").value("6:00:00 PM"));
    }

    @Test
    public void testGetSunriseSunset() throws Exception {
        double lat = 10.0;
        double lon = 20.0;
        SunriseSunsetReport mockReport = new SunriseSunsetReport(new SunriseSunsetResults("6:00:00 AM", "6:00:00 PM"));

        Mockito.when(latAndLonService.getLatAndLonFromCity(Mockito.anyString())).thenReturn(new LatAndLonReport(lat, lon));
        Mockito.when(sunriseSunsetService.getSunriseSunset(lat, lon)).thenReturn(mockReport);

        mockMvc.perform(get("/solar-watch")
                        .param("city", "SampleCity")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results.sunrise").value("6:00:00 AM"))
                .andExpect(jsonPath("$.results.sunset").value("6:00:00 PM"));
    }
}