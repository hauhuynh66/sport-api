package com.server.service.cfb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.server.document.cfb.CFBTeam;
import com.server.exception.NoRecordException;
import com.server.repository.cfb.CFBTeamRepository;

@Service
public class CFBTeamService {
    @Autowired
    private CFBTeamRepository cfbTeamRepository;

    public void save(CFBTeam team) {
        cfbTeamRepository.save(team);
    }

    public List<CFBTeam> findAll() {
        return cfbTeamRepository.getAll(Sort.by("conference"), Pageable.unpaged());
    }

    public CFBTeam findByCode(String code) throws NoRecordException {
        return cfbTeamRepository.getByCode(code);
    }

    public void clear() {
        cfbTeamRepository.clear();
    }
}
