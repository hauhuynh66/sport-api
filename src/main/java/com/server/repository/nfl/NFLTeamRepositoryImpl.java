package com.server.repository.nfl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.server.exception.QueryParamException;
import com.server.model.nfl.NFLTeam;

@Repository
public class NFLTeamRepositoryImpl implements NFLTeamRepository {
    @Autowired
    private MongoTemplate template;
    
    private List<String> divisionList = Arrays.asList("West", "East", "South", "North");
    private List<String> conferenceList = Arrays.asList("NFC", "AFC");

    @Override
    public void save(NFLTeam team) {
        if(getByName(team.getName()) != null) {
            return;
        }

        template.save(team);
    }

    @Override
    public NFLTeam getByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));

        return template.findOne(query, NFLTeam.class);
    }

    @Override
    public List<NFLTeam> getAll() {
        return template.findAll(NFLTeam.class);
    }

    @Override
    public void clear() {
        Query query = new Query();

        template.remove(query, "nfl_team");
    }

    @Override
    public List<NFLTeam> getByDivision(String division) throws QueryParamException {
        if(!divisionList.contains(division)) {
            throw new QueryParamException("Division " + division + " not exists");
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("division").is(division));

        return template.find(query, NFLTeam.class);
    }

    @Override
    public List<NFLTeam> getByConference(String conference) throws QueryParamException {
        if(!conferenceList.contains(conference)) {
            throw new QueryParamException("Conference " + conference + " not exists");
        }
        
        Query query = new Query();
        query.addCriteria(Criteria.where("conference").is(conference));

        return template.find(query, NFLTeam.class);
    }

    @Override
    public List<NFLTeam> getByDivisionAndConference(String division, String conference) throws QueryParamException {

        if(!divisionList.contains(division)) {
            throw new QueryParamException("Division " + division + " not exists");
        }

        if(!conferenceList.contains(conference)) {
            throw new QueryParamException("Conference " + conference + " not exists");
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("division").is(division).and("conference").is(conference));

        return template.find(query, NFLTeam.class);
    }
}
