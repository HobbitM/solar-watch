package com.example.solarwatch.sunrisesunset.model;

import com.example.solarwatch.city.model.City;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class SunriseSunset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sunrise_sunset_id;

    @ManyToOne
    @JoinColumn(name="city_id", referencedColumnName = "id")
    private City city;

    private LocalDate date;

    private String sunrise;
    private String sunset;



    public SunriseSunset(City city, LocalDate date, String sunrise, String sunset) {
        this.city = city;
        this.date = date;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }
}