package com.server.repository.general;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.server.document.general.Encyclopedia;

@Repository
public class EncyclopediaRepositoryImpl implements EncyclopediaRepository {
    @Autowired
    private MongoTemplate template;

    public String save(Encyclopedia enc) {
        Query query = new Query();
        query.addCriteria(
            Criteria.where("type")
                .is(enc.getType())
                .and("name").is(enc.getName())
                .and("code").is(enc.getCode()));
        Encyclopedia existed = template.findOne(query, Encyclopedia.class) ;
        if(existed == null) {
            Encyclopedia saved = template.save(enc);
            return saved.getId();
        }
        
        Document doc = new Document();
        template.getConverter().write(enc, doc);
        template.upsert(query, Update.fromDocument(doc), "encyclopedia");
        return existed.getId();
    }

    public List<Encyclopedia> getByType(String type) {
        Query query = new Query();

        query.addCriteria(Criteria.where("type").is(type));

        return template.find(query, Encyclopedia.class);
    }

    public List<Encyclopedia> getByTypeAndName(String type, String name) {
        Query query = new Query();

        query.addCriteria(Criteria.where("type").is(type).and("name").is(name));

        return template.find(query, Encyclopedia.class);
    }

    public List<Encyclopedia> getByTypeAndCode(String type, String code) {
        Query query = new Query();

        query.addCriteria(Criteria.where("type").is(type).and("code").is(code));

        return template.find(query, Encyclopedia.class);
    }

    public Encyclopedia getOne(String type, String name, String code) {
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is(type).and("name").is(name).and("code").is(code));

        return template.findOne(query, Encyclopedia.class);
    }

    public Encyclopedia getById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        return template.findOne(query, Encyclopedia.class);
    }

    @Override
    public void clear() {
        Query query = new Query();
        template.remove(query, Encyclopedia.class);
    }
}
