package com.server.service.geo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.server.model.geo.City;
import com.server.repository.geo.CityRepositoryImpl;

@Service
public class GeoLocationService {
    @Autowired
    private CityRepositoryImpl cityRepository;
    
    private static int _limit = 20;

    private static int _page = 0;

    public List<City> getByCityName(String name, int page) {
        Pageable pageable = PageRequest.of(page, this._limit);

        return cityRepository.getByCountry(name, pageable);
    }

    public City getByName(String asciiName) {

        return cityRepository.getByName(asciiName);
    }
}
