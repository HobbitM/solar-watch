package com.example.solarwatch.controller;

import com.example.solarwatch.city.model.City;
import com.example.solarwatch.city.service.CityService;
import com.example.solarwatch.sunrisesunset.model.UpdateDTO;
import com.example.solarwatch.sunrisesunset.service.SunriseSunsetService;
import com.example.solarwatch.sunrisesunset.model.SunriseSunset;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class SolarWatchController {

    private final CityService cityService;
    private final SunriseSunsetService sunriseSunsetService;

    @Autowired
    public SolarWatchController(CityService cityService, SunriseSunsetService sunriseSunsetService) {
        this.cityService = cityService;
        this.sunriseSunsetService = sunriseSunsetService;
    }

    @GetMapping("/solar-watch")
    public SunriseSunset getSunriseSunsetForDate(@RequestParam(required = false) LocalDate date, @RequestParam String city) {
        City cityEntity = cityService.getCityByName(city);
        SunriseSunset sunriseSunsetEntity;

        if (cityEntity == null) {
            cityEntity = cityService.getCityFromOpenWeatherAPI(city);
            cityService.addCity(cityEntity.getName(), cityEntity.getCountry(), cityEntity.getState(), cityEntity.getLat(), cityEntity.getLon());

            cityEntity = cityService.getCityByName(city);
        }

        if (date == null) {
            date = LocalDate.now();
        }

        sunriseSunsetEntity = sunriseSunsetService.getSunriseSunset(cityEntity, date);

        if (sunriseSunsetEntity == null) {
            sunriseSunsetEntity = sunriseSunsetService.getSunriseSunsetFromApi(cityEntity, cityEntity.getLat(), cityEntity.getLon(), date);
            sunriseSunsetService.addSunriseSunset(cityEntity, date, sunriseSunsetEntity.getSunrise(), sunriseSunsetEntity.getSunset());
            sunriseSunsetEntity = sunriseSunsetService.getSunriseSunset(cityEntity, date);
        }

        return sunriseSunsetEntity;
    }
    @PostMapping("/solar-watch")
    public void updateSunriseSunset(@RequestBody UpdateDTO updateDTO) {
        sunriseSunsetService.updateSunriseSunset(updateDTO);
    }

}
