package com.server.model.geo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

enum Capital {
    PRIMARY,
    ADMIN,
    MINOR
}

@Document(collection = "city")
@Getter
@Setter
public class City {
    @Id
    private String id;

    private String name;

    private Float longitude;

    private Float latitude;

    private long population;

    private Country country;

    public City(
        String name,
        Country country,
        Float longitude,
        Float latitude,
        long population
    ) {
        this.name = name;
        this.country = country;
        this.longitude = longitude;
        this.latitude = latitude;
        this.population = population;
    }
}
