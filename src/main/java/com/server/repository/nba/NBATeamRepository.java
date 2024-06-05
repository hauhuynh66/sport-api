package com.server.repository.nba;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.server.document.mlb.MLBTeam;

public interface NBATeamRepository {
    String save(MLBTeam team);
    MLBTeam getByCode(String code);
    List<MLBTeam> getByConferences(String conference, Sort sort);
    List<MLBTeam> getByDivison(String conference, String division);
    List<MLBTeam> getAll(Sort sort, Pageable pageable);
    void clear();
}
