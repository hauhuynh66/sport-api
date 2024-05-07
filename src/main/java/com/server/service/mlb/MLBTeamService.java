package com.server.service.mlb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.document.mlb.MLBTeam;
import com.server.exception.NoRecordException;
import com.server.exception.QueryParamException;
import com.server.repository.mlb.MLBTeamRepository;

@Service
public class MLBTeamService {
    @Autowired
    private MLBTeamRepository mlbTeamRepository;

    public void save(MLBTeam team) {
        mlbTeamRepository.save(team);
    }

    public MLBTeam findByCode(String code) throws NoRecordException {
        return mlbTeamRepository.getByCode(code);
    }

    public Map<String,String> teamList() {
        List<MLBTeam> teamList = findAll();
        Map<String, String> select = new HashMap<>();

        for (MLBTeam mlbTeam : teamList) {
            select.put(mlbTeam.getCode(), mlbTeam.getName());
        }

        return select;
    }

    public List<MLBTeam> findAllByDivision(String division, String league) throws QueryParamException {
        return mlbTeamRepository.getByDivison(division, league);
    }

    public List<MLBTeam> findAll() {
        return mlbTeamRepository.getAll();
    }
}
