package com.server.repository.nfl;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.server.document.nfl.NFLPlayer;

@Repository
public class NFLPlayerRepositoryImpl implements NFLPlayerRepository {
    @Autowired
    private MongoTemplate template;

    @Override
    public String save(NFLPlayer player) {
        Query query = new Query();
        query.addCriteria(
            Criteria
                .where("team").is(player.getTeam())
                .and("name").is(player.getName())
                .and("position").is(player.getPosition())
        );
        NFLPlayer existPlayer = template.findOne(query, NFLPlayer.class) ;
        if(existPlayer == null) {
            return template.save(player).getId();
        }

        Document doc = new Document();
        template.getConverter().write(player, doc);
        template.upsert(query, Update.fromDocument(doc), "nfl_player");
        return existPlayer.getId();
    }

    @Override
    public List<NFLPlayer> getByTeam(String teamCode, Sort sort) {
        Query query = new Query();
        query.addCriteria(
            Criteria.where("team").is(teamCode)
        ).with(sort);
        return template.find(query, NFLPlayer.class);
    }

    @Override
    public NFLPlayer getOne(String teamCode, String name, String position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOne'");
    }

    @Override
    public NFLPlayer getByTeamAndNumber(String teamCode, int number) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByTeamAndNumber'");
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }
    
}
