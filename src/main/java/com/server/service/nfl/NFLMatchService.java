package com.server.service.nfl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.repository.nfl.NFLMatchRepositoryImpl;

@Service
public class NFLMatchService {
    @Autowired
    private NFLMatchRepositoryImpl matchRepository;

    
}
