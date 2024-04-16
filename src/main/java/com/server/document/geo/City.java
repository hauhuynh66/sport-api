package com.server.document.geo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JsonIgnore
    private String id;

    private String name;

    private String asciiName;

    private Float longitude;

    private Float latitude;

    private long population;

    private Country country;

    public City(
        String name,
        String asciiName,
        Country country,
        Float longitude,
        Float latitude,
        long population
    ) {
        this.name = name;
        this.asciiName = asciiName;
        this.country = country;
        this.longitude = longitude;
        this.latitude = latitude;
        this.population = population;
    }
}
