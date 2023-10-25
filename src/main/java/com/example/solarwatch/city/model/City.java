package com.example.solarwatch.city.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String country;

    private String state;

    private double lat;

    private double lon;

    public City(String name, String country, String state, double lat, double lon) {
        this.name = name;
        this.country = country;
        this.state = state;
        this.lat = lat;
        this.lon = lon;
    }


}
