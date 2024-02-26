package com.server.service.nfl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.model.nfl.NFLMatch;
import com.server.repository.nfl.NFLMatchRepositoryImpl;

@Service
public class NFLMatchService {
    @Autowired
    private NFLMatchRepositoryImpl matchRepository;

    public List<NFLMatch> findAll() {
        return matchRepository.getSchedule("2023");
    }
}
