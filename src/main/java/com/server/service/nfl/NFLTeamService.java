package com.server.service.nfl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.repository.nfl.NFLTeamRepository;


@Service
public class NFLTeamService {
    @Autowired
    private NFLTeamRepository teamRepository;
    

}
