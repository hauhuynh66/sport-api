package com.server.repository.nfl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.server.document.nfl.NFLStadium;

@Repository
public class NFLStadiumRepository {
    @Autowired
    private MongoTemplate template;

    public void save(NFLStadium stadium) {
        template.save(stadium);
    }

    public void clear() {
        Query query = new Query();
        template.remove(query, NFLStadium.class);
    }
}
