package com.server.repository.geo;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.server.model.geo.City;

public interface CityRepository {
    void save(City city);
    List<City> getByCountry(String countryName, Pageable pageable);
    City getByName(String name);
    void clear();
}
