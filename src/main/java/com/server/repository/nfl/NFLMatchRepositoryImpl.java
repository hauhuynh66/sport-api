package com.server.repository.nfl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.server.model.nfl.NFLMatch;

@Repository
public class NFLMatchRepositoryImpl implements NFLMatchRepository {
    @Autowired
    private MongoTemplate template;

    @Override
    public void save(NFLMatch match) {
        template.save(match);
    }

    @Override
    public List<NFLMatch> getSchedule(String season) {
        Query query = new Query();
        query.addCriteria(Criteria.where("season").is(season));
        List<NFLMatch> matches = template.find(query, NFLMatch.class);
        
        return matches;
    }

    @Override
    public List<NFLMatch> getByTeamAndSeason(String team, String season) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByTeamAndSeason'");
    }

    @Override
    public List<NFLMatch> getByMatchupAndSeason(String team1, String team2, String season) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByMatchupAndSeason'");
    }

    @Override
    public void clear() {
        Query query = new Query();

        template.remove(query, "nfl_match");
    }

    @Override
    public NFLMatch getMatch(String team1, String team2, String date, String season) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMatch'");
    }
}
