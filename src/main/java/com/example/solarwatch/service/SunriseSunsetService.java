package com.example.solarwatch.service;

import com.example.solarwatch.model.SunriseSunsetReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
@Service
public class SunriseSunsetService {

    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(LatAndLonService.class);

    public SunriseSunsetService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SunriseSunsetReport getSunriseSunset(double lat, double lon, LocalDate date) {
        try {
            String url = String.format("https://api.sunrise-sunset.org/json?lat=%s&lng=%s&date=%s", lat, lon, date);

            SunriseSunsetReport response = restTemplate.getForObject(url, SunriseSunsetReport.class);

            if (response != null) {
                logger.info("Successfully retrieved sunrise-sunset data for date: {}", date);
                return new SunriseSunsetReport(response.results());
            } else {
                logger.error("Empty or null response from sunrise-sunset API.");
                throw new RuntimeException("Empty or null response from sunrise-sunset API.");
            }
        } catch (Exception e) {
            logger.error("Error while retrieving sunrise-sunset data for date: {}", e.getMessage());
            throw new RuntimeException("Error while retrieving sunrise-sunset data for date: " + e.getMessage());
        }
    }

    public SunriseSunsetReport getSunriseSunset(double lat, double lon) {
        try {
            String url = String.format("https://api.sunrise-sunset.org/json?lat=%s&lng=%s", lat, lon);

            SunriseSunsetReport response = restTemplate.getForObject(url, SunriseSunsetReport.class);

            if (response != null) {
                logger.info("Successfully retrieved sunrise-sunset data.");
                return new SunriseSunsetReport(response.results());
            } else {
                logger.error("Empty or null response from sunrise-sunset API.");
                throw new RuntimeException("Empty or null response from sunrise-sunset API.");
            }
        } catch (Exception e) {
            logger.error("Error while retrieving sunrise-sunset data: {}", e.getMessage());
            throw new RuntimeException("Error while retrieving sunrise-sunset data: " + e.getMessage());
        }
    }
}
