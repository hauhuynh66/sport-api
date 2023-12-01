package com.server.service.geo;

import lombok.Data;

@Data
public class CityByCountry {
    private String name;
    private Float longitude;
    private Float latitude;
    private long population;
}
