package com.server.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.server.model.nfl.NFLTeam;
import com.server.repository.nfl.NFLTeamRepository;

@Component
public class Preload implements CommandLineRunner {
    @Autowired
    private NFLTeamRepository nflTeam;

    @Override
    public void run(String... args) throws Exception {
        nflTeam.save(new NFLTeam("Buffalo Bills", "BUF", "East", "AFC", "http://localhost:8002/api/nfl/v1/team/buffalo.svg"));
    }
    
}
