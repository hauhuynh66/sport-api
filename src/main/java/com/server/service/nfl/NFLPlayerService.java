package com.server.service.nfl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.server.document.nfl.NFLPlayer;
import com.server.repository.nfl.NFLPlayerRepository;

@Service
public class NFLPlayerService {
    @Autowired
    private NFLPlayerRepository nflPlayerRepository;

    public String save(NFLPlayer player) {
        return nflPlayerRepository.save(player);
    }

    public List<NFLPlayer> findByTeam(String teamCode) {
        return nflPlayerRepository.getByTeam(teamCode, Sort.by("Position"));
    }
}
