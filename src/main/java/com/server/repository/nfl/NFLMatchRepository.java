package com.server.repository.nfl;

import java.util.List;

import com.server.document.nfl.NFLMatch;

public interface NFLMatchRepository {
    String save(NFLMatch match);
    NFLMatch getById(String id);
    List<NFLMatch> getBySeason(int season);
    List<NFLMatch> getByTeamAndSeason(String team, int season);
    List<NFLMatch> getByMatchup(String team1, String team2);
    NFLMatch getMatch(String team1, String team2, int round, int season);
    void clear();
}
