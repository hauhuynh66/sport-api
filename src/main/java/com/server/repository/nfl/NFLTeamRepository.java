package com.server.repository.nfl;

import java.util.List;

import com.server.model.nfl.NFLTeam;

public interface NFLTeamRepository{
    void save(NFLTeam team);
    boolean existsByName(String name);
    NFLTeam findByName(String name);
    List<NFLTeam> findAll();
}
