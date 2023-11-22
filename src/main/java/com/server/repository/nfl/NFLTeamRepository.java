package com.server.repository.nfl;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.server.model.nfl.NFLTeam;

@Repository
public interface NFLTeamRepository extends MongoRepository<NFLTeam, String> {
    
}
