package com.server.repository.general;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.server.document.general.ImageUrl;

@Repository
public class ImageUrlRepository {
    @Autowired
    private MongoTemplate template;

    public void save(ImageUrl imageUrl) {
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is(imageUrl.getType()).and("code").is(imageUrl.getCode()));
        ImageUrl url = template.findOne(query, ImageUrl.class) ;
        if(url == null) {
            template.save(imageUrl);
        } else {
            Document doc = new Document();
            template.getConverter().write(imageUrl, doc);
            template.upsert(query, Update.fromDocument(doc), "image_url");
        }
    }

    public List<ImageUrl> getByType(String type) {
        Query query = new Query();

        query.addCriteria(Criteria.where("type").is(type));

        return template.find(query, ImageUrl.class);
    }

    public ImageUrl getByTypeAndCode(String type, String code) {
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is(type).and("code").is(code));

        return template.findOne(query, ImageUrl.class);
    }

    public void clearType(String type) {
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is(type));
        template.remove(query, ImageUrl.class);
    }
}
