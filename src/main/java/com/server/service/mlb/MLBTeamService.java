package com.server.service.mlb;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.server.document.mlb.MLBTeam;
import com.server.exception.NoRecordException;
import com.server.exception.QueryParamException;
import com.server.lib.data.Pair;
import com.server.repository.mlb.MLBTeamRepository;

@Service
public class MLBTeamService {
    @Autowired
    private MLBTeamRepository mlbTeamRepository;

    public static List<String> DIVISIONS = Arrays.asList("Central", "East", "West");
    public static List<String> LEAGUES = Arrays.asList("American", "National");

    public void save(MLBTeam team) {
        mlbTeamRepository.save(team);
    }

    public MLBTeam findByCode(String code) throws NoRecordException {
        return mlbTeamRepository.getByCode(code);
    }

    public List<MLBTeam> list() {
        return findAll("name", true, 0);
    }

    public List<Pair<String, String>> teamList() {
        List<MLBTeam> teamList = findAll("name", true, 0);
        return teamList.stream().map(team -> new Pair<>(team.getCode(), team.getName())).collect(Collectors.toList());
    }

    public List<MLBTeam> findAllByDivision(String division, String league) throws QueryParamException {
        return mlbTeamRepository.getByDivison(division, league);
    }

    public List<MLBTeam> findAll(String sortBy, boolean descending, int records) {
        Sort sort = Sort.by(sortBy);
        Pageable pageable;
        if(!descending) {
            sort.descending();
        }
        if(records > 0) {
            pageable = Pageable.ofSize(records);
        } else {
            pageable = Pageable.unpaged();
        }
        return mlbTeamRepository.getAll(sort, pageable);
    }

    public void clear() {
        mlbTeamRepository.clear();
    }
}
