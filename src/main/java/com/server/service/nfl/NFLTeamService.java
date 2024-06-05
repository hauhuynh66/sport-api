package com.server.service.nfl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.server.document.nfl.NFLTeam;
import com.server.exception.NoRecordException;
import com.server.exception.QueryParamException;
import com.server.repository.general.EncyclopediaRepository;
import com.server.repository.nfl.NFLTeamRepository;

@Service
public class NFLTeamService {
    @Autowired
    private NFLTeamRepository repository;

    @Autowired
    private EncyclopediaRepository encRepository;

    public static List<String> DIVISIONS = Arrays.asList("West", "East", "South", "North");
    public static List<String> CONFERENCES = Arrays.asList("NFC", "AFC");

    public String save(NFLTeam team) {
        return repository.save(team);
    }

    public NFLTeam findByCode(String code) throws NoRecordException {
        NFLTeam team = repository.getByCode(code);
        if(team == null) {
            throw new NoRecordException("No NFL team with code " + code);
        }
        return team;
    }

    public List<NFLTeam> findByConference(String conference) throws QueryParamException {
        if(!CONFERENCES.contains(conference)) {
            throw new QueryParamException("NFL conference named " + conference + " does not exists");
        }
        return repository.getByConference(conference);
    }

    public List<NFLTeam> findByDivision(String conference, String division) throws QueryParamException {
        if(!CONFERENCES.contains(conference)) {
            throw new QueryParamException("NFL conference named " + conference + " does not exists");
        }

        if(!DIVISIONS.contains(division)) {
            throw new QueryParamException("NFL division named " + division + " does not exists");
        }

        return repository.getByDivision(conference, division);
    }

    public List<NFLTeam> findAll() {
        return repository.getAll(Sort.by("name").ascending(), Pageable.unpaged());
    }
    
    public void clear() {
        repository.clear();
    }
}
