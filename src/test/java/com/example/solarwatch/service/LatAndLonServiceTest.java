package com.example.solarwatch.service;

import com.example.solarwatch.model.LatAndLonReport;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class LatAndLonServiceTest {

    @Autowired
    private LatAndLonService latAndLonService;

    @MockBean
    private RestTemplate restTemplate;


    @Test
    void testGetLatAndLonFromCity() {
        String city = "SampleCity";
        LatAndLonReport mockReport = new LatAndLonReport(10.0, 20.0);
        ResponseEntity<LatAndLonReport[]> mockResponseEntity = new ResponseEntity<>(new LatAndLonReport[]{mockReport}, HttpStatus.OK);

        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.eq(LatAndLonReport[].class)))
                .thenReturn(mockResponseEntity);


        LatAndLonReport result = latAndLonService.getLatAndLonFromCity(city);

        assertNotNull(result);
        assertEquals(10.0, result.lat(), 0.01);
        assertEquals(20.0, result.lon(), 0.01);
    }
}