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

import com.server.document.nfl.NFLMatch;

@Repository
public class NFLMatchRepositoryImpl implements NFLMatchRepository {
    @Autowired
    private MongoTemplate template;

    @Override
    public void save(NFLMatch match) {
        List<String> teams = Arrays.asList(match.getTeamOne(), match.getTeamTwo());
        Query query = new Query();
        query.addCriteria(
            Criteria.where("season")
                    .is(match.getSeason())
                    .and("teamOne").in(teams)
                    .and("teamTwo").in(teams)
                    .and("date").is(match.getDate())
        );
        NFLMatch existMatch = template.findOne(query, NFLMatch.class) ;
        if(existMatch == null) {
            template.save(match);
        } else {
            Document doc = new Document();
            template.getConverter().write(match, doc);
            template.upsert(query, Update.fromDocument(doc), "nfl_match");
        }
    }

    @Override
    public NFLMatch getById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return template.findOne(query, NFLMatch.class);
    }

    @Override
    public List<NFLMatch> getBySeason(int season) {
        Query query = new Query();
        query.addCriteria(Criteria.where("season").is(season));
        List<NFLMatch> matches = template.find(query, NFLMatch.class);
        
        return matches;
    }

    @Override
    public List<NFLMatch> getByTeamAndSeason(String team, int season) {
        Query query = new Query();
        query.addCriteria(
            Criteria.where("season")
                    .is(season)
                    .orOperator(Criteria.where("teamOne").is(team), Criteria.where("teamTwo").is(team))
        );

        return template.find(query, NFLMatch.class);
    }

    @Override
    public List<NFLMatch> getByMatchup(String team1, String team2) {
        List<String> teams = Arrays.asList(team1, team2);

        Query query = new Query();
        query.addCriteria(
            Criteria.where("teamOne").in(teams).and("teamTwo").in(teams)
        );

        return template.find(query, NFLMatch.class);
    }

    @Override
    public NFLMatch getMatch(String team1, String team2, String date, int season) {
        List<String> teams = Arrays.asList(team1, team2);

        Query query = new Query();
        query.addCriteria(
            Criteria.where("teamOne").in(teams).and("teamTwo").in(teams).and("season").is(season).and("date").is(date)
        );

        return template.findOne(query, NFLMatch.class);
    }

    @Override
    public void clear() {
        Query query = new Query();

        template.remove(query, "nfl_match");
    }

    
}
