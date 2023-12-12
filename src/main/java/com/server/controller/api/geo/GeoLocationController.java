package com.server.controller.api.geo;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.model.geo.City;
import com.server.service.geo.CityByCountry;
import com.server.service.geo.GeoLocationService;

@RestController
@RequestMapping("/api/v1/geo")
public class GeoLocationController {
    @Autowired
    private GeoLocationService geoLocationService;

    @Autowired
    private ModelMapper mapper;
    
    @GetMapping("/list")
    private List<CityByCountry> cityInCountry(
        @RequestParam(required = true, name = "country") String name,
        @RequestParam(required = false, name = "page", defaultValue = "0") int page
    ) {
        List<City> cities = geoLocationService.getByCityName(name, page);

        return cities.stream().map(this::removeCountry).collect(Collectors.toList());
    }

    
    @GetMapping("/city")
    private City cityInCountry(
        @RequestParam(required = true, name = "name") String name
    ) {
        return geoLocationService.getByName(name);
    }

    private CityByCountry removeCountry(City city) {
        return mapper.map(city, CityByCountry.class);
    }
}
