package com.server.repository.nfl;

import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.server.document.nfl.NFLTeam;
import com.server.exception.NoRecordException;
import com.server.exception.QueryParamException;

@Repository
public class NFLTeamRepositoryImpl implements NFLTeamRepository {
    @Autowired
    private MongoTemplate template;
    
    private List<String> divisionList = Arrays.asList("West", "East", "South", "North");
    private List<String> conferenceList = Arrays.asList("NFC", "AFC");

    @Override
    public void save(NFLTeam team) {
        Query query = new Query();
        query.addCriteria(Criteria.where("code").is(team.getCode()));
        NFLTeam existTeam = template.findOne(query, NFLTeam.class) ;
        if(existTeam == null) {
            template.save(team);
        } else {
            Document doc = new Document();
            template.getConverter().write(team, doc);
            template.upsert(query, Update.fromDocument(doc), "nfl_team");
        }
    }

    @Override
    public NFLTeam getByCode(String code) throws NoRecordException {
        Query query = new Query();
        query.addCriteria(Criteria.where("code").is(code));
        NFLTeam team = template.findOne(query, NFLTeam.class);
        if(team == null) {
            throw new NoRecordException("Cannot find NFL team with code " + code);
        }

        return team;
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
    public List<NFLTeam> getByConference(String conference) throws QueryParamException {
        if(!conferenceList.contains(conference)) {
            throw new QueryParamException("Conference " + conference + " not exists");
        }
        
        Query query = new Query();
        query.addCriteria(Criteria.where("conference").is(conference));

        return template.find(query, NFLTeam.class);
    }

    @Override
    public List<NFLTeam> getByDivision(String division, String conference) throws QueryParamException {

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
