package com.server.repository.geo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.server.model.geo.City;

@Repository
public class CityRepositoryImpl implements CityRepository {
    @Autowired
    private MongoTemplate template;

    @Override
    public void save(City city) {
        template.save(city);
    }

    @Override
    public List<City> getByCountry(String countryName, Pageable pageable) {
        
        Query query = new Query().with(pageable);

        query.addCriteria(Criteria.where("country.name").is(countryName));

        return template.find(query, City.class);
    }

    @Override
    public void clear() {
        Query query = new Query();

        template.remove(query, "city");
    }
    
}
