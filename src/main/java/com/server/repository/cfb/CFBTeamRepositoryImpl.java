package com.server.repository.cfb;

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

import com.server.document.cfb.CFBTeam;

@Repository
public class CFBTeamRepositoryImpl implements CFBTeamRepository{
    @Autowired
    private MongoTemplate template;

    public String save(CFBTeam team) {
        Query query = new Query();
        query.addCriteria(Criteria.where("school").is(team.getSchool()));
        CFBTeam existTeam = template.findOne(query, CFBTeam.class) ;
        if(existTeam == null) {
            return template.save(team).getId();
        }
        Document doc = new Document();
        template.getConverter().write(team, doc);
        template.upsert(query, Update.fromDocument(doc), "cfb_team");
        return existTeam.getId();
    }

    public CFBTeam getByCode(String code) {
        Query query = new Query();
        query.addCriteria(Criteria.where("code").is(code));

        return template.findOne(query, CFBTeam.class);
    }

    public List<CFBTeam> getByConference(String conference) {
        Query query = new Query();
        query.addCriteria(Criteria.where("conference").is(conference));

        return template.find(query, CFBTeam.class);
    }

    public List<CFBTeam> getAll(Sort sort, Pageable pageable) {
        Query query = new Query();
        query.with(sort).with(pageable);
        return template.find(query, CFBTeam.class);
    }

    public void clear() {
        Query query = new Query();

        template.remove(query, "cfb_team");
    }
}
