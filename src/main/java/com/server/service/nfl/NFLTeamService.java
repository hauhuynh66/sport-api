package com.server.service.nfl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.exception.NoRecordException;
import com.server.exception.QueryParamException;
import com.server.model.nfl.NFLTeam;
import com.server.repository.nfl.NFLTeamRepositoryImpl;

@Service
public class NFLTeamService {
    @Autowired
    private NFLTeamRepositoryImpl nflTeamRepository;

    public void save(NFLTeam team) {
        nflTeamRepository.save(team);
    }

    public NFLTeam findByName(String name) throws NoRecordException {
        return nflTeamRepository.getByName(name);
    }

    public List<NFLTeam> findByGroup(String division, String conference) throws QueryParamException {
        if(division == null && conference == null) {
            return findAll();
        }

        if(division != null && conference !=null) {
            return nflTeamRepository.getByDivisionAndConference(division, conference);
        }

        if(division == null) {
            return nflTeamRepository.getByConference(conference);
        }

        return nflTeamRepository.getByDivision(division);
    }

    public List<NFLTeam> findAll() {
        return nflTeamRepository.getAll();
    }
}
