package com.server.service.nfl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.model.nfl.NFLTeam;
import com.server.repository.nfl.NFLTeamRepositoryImpl;


@Service
public class NFLTeamService {
    @Autowired
    private NFLTeamRepositoryImpl nflTeamRepository;

    public void save(NFLTeam team) {
        nflTeamRepository.save(team);
    }

    public NFLTeam findByName(String name) {
        return nflTeamRepository.getByName(name);
    }

    public List<NFLTeam> findAll() {
        return nflTeamRepository.getAll();
    }
}
