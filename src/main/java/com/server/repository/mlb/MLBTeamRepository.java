package com.server.repository.mlb;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.server.document.mlb.MLBTeam;

public interface MLBTeamRepository {
    String save(MLBTeam team);
    MLBTeam getByCode(String code);
    List<MLBTeam> getByLeague(String league, Sort sort);
    List<MLBTeam> getByDivison(String division, String league);
    List<MLBTeam> getAll(Sort sort, Pageable pageable);
    void clear();
}
