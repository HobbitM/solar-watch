package com.example.solarwatch.sunrisesunset.repository;

import com.example.solarwatch.city.model.City;
import com.example.solarwatch.sunrisesunset.model.SunriseSunset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SunriseSunsetRepository extends JpaRepository<SunriseSunset, Integer> {
    Optional<SunriseSunset> findByCityAndDate(City city, LocalDate date);
}