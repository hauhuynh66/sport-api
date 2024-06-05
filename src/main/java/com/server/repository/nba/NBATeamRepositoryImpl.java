package com.server.repository.nba;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.server.document.mlb.MLBTeam;

@Repository
public class NBATeamRepositoryImpl implements NBATeamRepository {

    @Override
    public String save(MLBTeam team) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public MLBTeam getByCode(String code) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByCode'");
    }

    @Override
    public List<MLBTeam> getByConferences(String conference, Sort sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByConferences'");
    }

    @Override
    public List<MLBTeam> getByDivison(String conference, String division) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByDivison'");
    }

    @Override
    public List<MLBTeam> getAll(Sort sort, Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }

}
