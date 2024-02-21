package com.server.repository.nfl;

import java.util.List;

import com.server.model.nfl.NFLMatch;

public interface NFLMatchRepository {
    void save(NFLMatch match);
    List<NFLMatch> getSchedule(String season);
    List<NFLMatch> getByTeamAndSeason(String team, String season);
    List<NFLMatch> getByMatchupAndSeason(String team1, String team2, String season);
    NFLMatch getMatch(String team1, String team2, String date, String season);
    void clear();
}
