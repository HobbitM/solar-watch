package com.example.solarwatch.service;

import com.example.solarwatch.model.LatAndLonReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class LatAndLonService {

    @Value("${openweather.api.key}")
    private String API_KEY;

    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(LatAndLonService.class);

    public LatAndLonService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public LatAndLonReport getLatAndLonFromCity(String city) {
        try {
            String url = String.format("https://api.openweathermap.org/geo/1.0/direct?q=%s&&appid=%s", city, API_KEY);

            ResponseEntity<LatAndLonReport[]> responseEntity = restTemplate.getForEntity(url, LatAndLonReport[].class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                LatAndLonReport[] responseArray = responseEntity.getBody();
                if (responseArray != null && responseArray.length > 0) {
                    LatAndLonReport latAndLonReport = new LatAndLonReport(responseArray[0].lat(), responseArray[0].lon());
                    logger.info("Successfully retrieved latitude and longitude: {}", latAndLonReport);
                    return latAndLonReport;
                } else {
                    logger.error("Empty or null response from Open Weather API.");
                    throw new RuntimeException("Empty or null response from Open Weather API.");
                }
            } else {
                logger.error("Error response from Open Weather API: {}", responseEntity.getStatusCode());
                throw new RuntimeException("Error response from Open Weather API: " + responseEntity.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("Error while retrieving latitude and longitude: {}", e.getMessage());
            throw new RuntimeException("Error while retrieving latitude and longitude: " + e.getMessage());
        }
    }
}
