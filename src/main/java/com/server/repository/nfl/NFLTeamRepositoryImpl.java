package com.server.repository.nfl;

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

import com.server.document.nfl.NFLTeam;

@Repository
public class NFLTeamRepositoryImpl implements NFLTeamRepository {
    @Autowired
    private MongoTemplate template;

    @Override
    public String save(NFLTeam team) {
        Query query = new Query();
        query.addCriteria(Criteria.where("code").is(team.getCode()));
        NFLTeam existTeam = template.findOne(query, NFLTeam.class) ;
        if(existTeam == null) {
            return template.save(team).getId();
        }
        Document doc = new Document();
        template.getConverter().write(team, doc);
        template.upsert(query, Update.fromDocument(doc), "nfl_team");
        return existTeam.getId();
    }

    @Override
    public NFLTeam getByCode(String code) {
        Query query = new Query();
        query.addCriteria(Criteria.where("code").is(code));

        return template.findOne(query, NFLTeam.class);
    }

    @Override
    public List<NFLTeam> getAll(Sort sort, Pageable pageable) {
        Query query = new Query();
        query.with(sort).with(pageable);
        return template.find(query, NFLTeam.class);
    }

    @Override
    public List<NFLTeam> getByConference(String conference){
        Query query = new Query();
        query.addCriteria(Criteria.where("conference").is(conference));
        query.with(Sort.by("name").ascending());

        return template.find(query, NFLTeam.class);
    }

    @Override
    public List<NFLTeam> getByDivision(String conference, String division) {
        Query query = new Query();
        query.addCriteria(Criteria.where("division").is(division).and("conference").is(conference));
        query.with(Sort.by("name").ascending());

        return template.find(query, NFLTeam.class);
    }

    @Override
    public void clear() {
        Query query = new Query();

        template.remove(query, "nfl_team");
    }
}
