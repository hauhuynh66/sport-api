package com.server.service.nfl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.document.nfl.NFLTeam;
import com.server.exception.NoRecordException;
import com.server.exception.QueryParamException;
import com.server.repository.nfl.NFLTeamRepositoryImpl;

@Service
public class NFLTeamService {
    @Autowired
    private NFLTeamRepositoryImpl nflTeamRepository;

    public void save(NFLTeam team) {
        nflTeamRepository.save(team);
    }

    public NFLTeam findByCode(String code) throws NoRecordException {
        return nflTeamRepository.getByCode(code);
    }

    public Map<String,String> teamNames() {
        List<NFLTeam> teamList = findAll();
        Map<String, String> select = new HashMap<>();

        for (NFLTeam nflTeam : teamList) {
            select.put(nflTeam.getCode(), nflTeam.getFullName());
        }

        return select;
    }

    public List<NFLTeam> findByDivision(String division, String conference) throws QueryParamException {
        if(division == null && conference == null) {
            return findAll();
        }

        if(division == null && conference != null) {
            return nflTeamRepository.getByConference(conference);
        }

        if(division != null && conference !=null) {
            return nflTeamRepository.getByDivision(division, conference);
        }

        throw new QueryParamException("Please enter division for conference " + conference);
    }

    public List<NFLTeam> findAll() {
        return nflTeamRepository.getAll();
    }
}
