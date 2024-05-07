package com.server.repository.mlb;

import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.server.document.mlb.MLBTeam;
import com.server.exception.NoRecordException;
import com.server.exception.QueryParamException;

@Repository
public class MLBTeamRepository {
    @Autowired
    private MongoTemplate template;

    private List<String> divisions = Arrays.asList("Central", "East", "West");
    private List<String> leagues = Arrays.asList("American", "National");

    public void save(MLBTeam team) {
        Query query = new Query();
        query.addCriteria(Criteria.where("code").is(team.getCode()));
        MLBTeam existTeam = template.findOne(query, MLBTeam.class) ;
        if(existTeam == null) {
            template.save(team);
        } else {
            Document doc = new Document();
            template.getConverter().write(team, doc);
            template.upsert(query, Update.fromDocument(doc), "mlb_team");
        }
    }

    public MLBTeam getByCode(String code) throws NoRecordException {
        Query query = new Query();
        query.addCriteria(Criteria.where("code").is(code));
        MLBTeam team = template.findOne(query, MLBTeam.class);
        if(team == null) {
            throw new NoRecordException("Cannot find MLB team with code " + code);
        }

        return team;
    }

    public List<MLBTeam> getByLeague(String league) throws QueryParamException {
        if(!leagues.contains(league)) {
            throw new QueryParamException(league + " leage " + " not exists");
        }
        
        Query query = new Query();
        query.addCriteria(Criteria.where("league").is(league));

        return template.find(query, MLBTeam.class);
    }

    public List<MLBTeam> getByDivison(String division, String league) throws QueryParamException {
        if(!divisions.contains(division)) {
            throw new QueryParamException("Division " + division + " not exists");
        }

        if(!leagues.contains(league)) {
            throw new QueryParamException(league + " leage " + " not exists");
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("division").is(division).and("league").is(league));

        return template.find(query, MLBTeam.class);
    }

    public List<MLBTeam> getAll() {
        return template.findAll(MLBTeam.class);
    }

    public void clear() {
        Query query = new Query();

        template.remove(query, "mlb_team");
    }
}
