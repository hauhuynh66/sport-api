package com.server.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.server.model.user.User;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private MongoTemplate template;

    @Override
    public void save(User user) {
        boolean isExisted = getByEmail(user.getEmail()) != null;

        if(isExisted) {
            return;
        }

        template.save(user);
    }

    @Override
    public User getByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));

        return template.findOne(query, User.class);
    }

    @Override
    public void clear() {
        Query query = new Query();
        template.remove(query, "user");
    }
    
}
