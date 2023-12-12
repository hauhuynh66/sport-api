package com.server.batch;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.server.model.geo.City;
import com.server.model.geo.Country;
import com.server.repository.geo.CityRepositoryImpl;
import com.server.storage.StorageService;

@Component
public class GeoLocationBatch implements CommandLineRunner{
    Logger logger = LoggerFactory.getLogger(GeoLocationBatch.class);

    @Value("${data.geo.init}")
    private boolean init = false;

    @Autowired
    private CityRepositoryImpl cityRepository;

    @Autowired
    private StorageService storageService;

    @Override
    public void run(String... args) throws Exception {
        if(init) {
            cityRepository.clear();

            File file = storageService.load("worldcities.csv").toFile();

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setQuote('"')
                .setHeader()
                .setSkipHeaderRecord(true)
                .build();

            Iterable<CSVRecord> records = csvFormat.parse(new FileReader(file, Charset.forName("utf-16le")));

            for (CSVRecord record : records) {
                Country country = new Country(
                    record.get(4),
                    record.get(5)
                );

                long population = 0;

                try {
                    population = Long.valueOf(record.get(9));
                } catch( NumberFormatException e) {}
                
                City city = new City(
                    record.get(0),
                    record.get(1),
                    country,
                    Float.valueOf(record.get(2)),
                    Float.valueOf(record.get(3)),
                    population
                );

                this.cityRepository.save(city);
            }
        }
    }
    
}
