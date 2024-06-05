package com.server.repository.user;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.server.document.user.Admin;
import com.server.exception.NoRecordException;

@Repository
public class AdminRepository {
    @Autowired
    private MongoTemplate template;

    public Admin getByUsername(String username) throws NoRecordException {
        Query query = new Query();
        Admin admin = template.findOne(query, Admin.class);

        if(admin == null) {
            throw new NoRecordException("No account with username '" + username + "' found");
        }

        return admin;
    }

    public void save(Admin admin) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(admin.getUsername()));
        Admin existed = template.findOne(query, Admin.class) ;
        if(existed == null) {
            template.save(admin);
        } else {
            Document doc = new Document();
            template.getConverter().write(admin, doc);
            template.upsert(query, Update.fromDocument(doc), "admin");
        }
    }

    public void clear() {
        Query query = new Query();

        template.remove(query, "admin");
    }
}