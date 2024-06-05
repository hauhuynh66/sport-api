package com.server.repository.nfl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.server.document.nfl.NFLTeam;

public interface NFLTeamRepository {
    String save(NFLTeam team);
    NFLTeam getByCode(String code);
    List<NFLTeam> getAll(Sort sort, Pageable pageable);
    List<NFLTeam> getByConference(String conference);
    List<NFLTeam> getByDivision(String conference, String division);
    void clear();
}
