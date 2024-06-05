package com.server.repository.mlb;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.server.document.mlb.MLBTeam;

@Repository
public class MLBTeamRepositoryImpl implements MLBTeamRepository {
    @Autowired
    private MongoTemplate template;

    public String save(MLBTeam team) {
        Query query = new Query();
        query.addCriteria(Criteria.where("code").is(team.getCode()));
        MLBTeam existTeam = template.findOne(query, MLBTeam.class) ;
        if(existTeam == null) {
            return template.save(team).getId();
        }
        Document doc = new Document();
        template.getConverter().write(team, doc);
        template.upsert(query, Update.fromDocument(doc), "mlb_team");
        return existTeam.getId();
    }

    public MLBTeam getByCode(String code) {
        Query query = new Query();
        query.addCriteria(Criteria.where("code").is(code));
        return template.findOne(query, MLBTeam.class);
    }

    public List<MLBTeam> getByLeague(String league, Sort sort) {
        Query query = new Query();
        query.addCriteria(Criteria.where("league").is(league));
        query.with(sort);

        return template.find(query, MLBTeam.class);
    }

    public List<MLBTeam> getByDivison(String division, String league) {
        Query query = new Query();
        query.addCriteria(Criteria.where("division").is(division).and("league").is(league));

        return template.find(query, MLBTeam.class);
    }

    public List<MLBTeam> getAll(Sort sort, Pageable pageable) {
        Query query = new Query();
        query.with(sort).with(pageable);
        return template.find(query, MLBTeam.class);
    }

    public void clear() {
        Query query = new Query();

        template.remove(query, "mlb_team");
    }
}
