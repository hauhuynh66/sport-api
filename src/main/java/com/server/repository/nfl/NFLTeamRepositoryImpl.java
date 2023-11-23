package com.server.repository.nfl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.server.model.nfl.NFLTeam;

@Repository
public class NFLTeamRepositoryImpl implements NFLTeamRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(NFLTeam team) {
        if(existsByName(team.getName())) {
            return;
        }

        mongoTemplate.save(team);
    }

    @Override
    public boolean existsByName(String name) {
        Query query = new Query();

        query.addCriteria(Criteria.where("name").is(name));

        return mongoTemplate.exists(query, "NFLTeam");
    }

    @Override
    public NFLTeam findByName(String name) {
        if(!existsByName(name)) {
            return null;
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));

        return mongoTemplate.findOne(query, NFLTeam.class);
    }

    @Override
    public List<NFLTeam> findAll() {
        return mongoTemplate.findAll(NFLTeam.class);
    }
}
