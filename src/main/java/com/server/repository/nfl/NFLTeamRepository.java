package com.server.repository.nfl;

import java.util.List;

import com.server.exception.NoRecordException;
import com.server.exception.QueryParamException;
import com.server.model.nfl.NFLTeam;

public interface NFLTeamRepository{
    void save(NFLTeam team);
    NFLTeam getByName(String name) throws NoRecordException;
    List<NFLTeam> getAll();
    List<NFLTeam> getByDivision(String division) throws QueryParamException;
    List<NFLTeam> getByConference(String conference) throws QueryParamException;
    List<NFLTeam> getByDivisionAndConference(String division, String conference) throws QueryParamException;
    void clear();
}
