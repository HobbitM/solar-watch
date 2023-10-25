package com.example.solarwatch.city.repository;

import com.example.solarwatch.city.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    City findByNameIgnoreCase(String name);
}
