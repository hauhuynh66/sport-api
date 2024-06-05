package com.server.repository.cfb;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.server.document.cfb.CFBTeam;

public interface CFBTeamRepository {
    String save(CFBTeam team);
    CFBTeam getByCode(String code);
    List<CFBTeam> getByConference(String conferences);
    List<CFBTeam> getAll(Sort sort, Pageable page);
    void clear();
}
