package com.server.service.nba;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.repository.nba.NBATeamRepository;

@Service
public class NBATeamService {
    @Autowired
    private NBATeamRepository nbaTeamRepository;
    
}
