package com.example.solarwatch.controller;


import com.example.solarwatch.model.LatAndLonReport;
import com.example.solarwatch.model.SunriseSunsetReport;
import com.example.solarwatch.service.LatAndLonService;
import com.example.solarwatch.service.SunriseSunsetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class SolarWatchController {

    private final LatAndLonService latAndLonService;

    private final SunriseSunsetService sunriseSunsetService;

    @Autowired
    public SolarWatchController(LatAndLonService latAndLonService, SunriseSunsetService sunriseSunsetService) {
        this.latAndLonService = latAndLonService;
        this.sunriseSunsetService = sunriseSunsetService;
    }


    @GetMapping("/solar-watch")
    public SunriseSunsetReport getSunriseSunsetForDate(@RequestParam(required = false) LocalDate date, String city){
        LatAndLonReport latAndLonReport = latAndLonService.getLatAndLonFromCity(city);

        if (date!=null) {
            return sunriseSunsetService.getSunriseSunset(latAndLonReport.lat(), latAndLonReport.lon(), date);
        }
        return sunriseSunsetService.getSunriseSunset(latAndLonReport.lat(), latAndLonReport.lon());
    }


}
