package com.server.repository.nfl;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.server.document.nfl.NFLPlayer;

public interface NFLPlayerRepository {
    String save(NFLPlayer player);
    List<NFLPlayer> getByTeam(String teamCode, Sort sort);
    NFLPlayer getOne(String teamCode, String name, String position);
    NFLPlayer getByTeamAndNumber(String teamCode, int number);
    void clear();
}
