package com.example.solarwatch.sunrisesunset.service;

import com.example.solarwatch.city.model.City;
import com.example.solarwatch.sunrisesunset.model.SunriseSunset;
import com.example.solarwatch.sunrisesunset.model.SunriseSunsetResponse;
import com.example.solarwatch.sunrisesunset.model.UpdateDTO;
import com.example.solarwatch.sunrisesunset.repository.SunriseSunsetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;


@Service
public class SunriseSunsetService {

    private final RestTemplate restTemplate;

    private final SunriseSunsetRepository sunriseSunsetRepository;


@Autowired
    public SunriseSunsetService(RestTemplate restTemplate, SunriseSunsetRepository sunriseSunsetRepository) {
        this.restTemplate = restTemplate;
        this.sunriseSunsetRepository = sunriseSunsetRepository;
    }

    public SunriseSunset getSunriseSunsetFromApi(City city, double lat, double lon, LocalDate date) {
          try {
            String url = String.format("https://api.sunrise-sunset.org/json?lat=%s&lng=%s&date=%s", lat, lon, date);

            ResponseEntity<SunriseSunsetResponse> responseEntity = restTemplate.getForEntity(url, SunriseSunsetResponse.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                SunriseSunsetResponse sunriseSunsetResponse = responseEntity.getBody();
                if (sunriseSunsetResponse != null && sunriseSunsetResponse.getResults() != null) {
                    String sunrise = sunriseSunsetResponse.getResults().getSunrise();
                    String sunset = sunriseSunsetResponse.getResults().getSunset();

                    return new SunriseSunset(city, date, sunrise, sunset);
                } else {
                    throw new RuntimeException("Empty or null response from Sunrise-Sunset API.");
                }
            } else {
                throw new RuntimeException("Error response from Sunrise-Sunset API: " + responseEntity.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while retrieving sunrise-sunset data for date: " + e.getMessage());
        }
    }


    public void addSunriseSunset(City city,LocalDate date , String sunrise, String sunset){
    sunriseSunsetRepository.save(new SunriseSunset(city, date, sunrise, sunset));
    }
    //zwracac optional
    public SunriseSunset getSunriseSunset(City city, LocalDate date) {
        return sunriseSunsetRepository.findByCityAndDate(city, date)
                .orElse(null);
    }

    public void updateSunriseSunset(UpdateDTO updateDTO){
    Optional<SunriseSunset> sunriseSunset = sunriseSunsetRepository.findById(updateDTO.id());
    if (sunriseSunset.isPresent()){
        sunriseSunset.get().setSunset(updateDTO.sunset());
        sunriseSunset.get().setSunrise((updateDTO.sunrise()));
        sunriseSunsetRepository.save(sunriseSunset.get());
    }
    }
}
