package com.server.controller.api.nfl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.document.nfl.NFLTeam;
import com.server.exception.NoRecordException;
import com.server.exception.QueryParamException;
import com.server.service.nfl.NFLTeamService;

@RestController()
@RequestMapping("/api/v1/nfl/team")
public class NFLTeamController {
    @Autowired
    private NFLTeamService teamService;

    @GetMapping("/all")
    private List<NFLTeam> getAll() {
        return teamService.findAll();
    }

    @GetMapping("/group")
    private List<NFLTeam> getByGroup(
        @RequestParam(name = "division", required = false) String division,
        @RequestParam(name = "conference", required = false) String conference
    ) throws QueryParamException {
        return teamService.findByGroup(division, conference);
    }

    @GetMapping("/info")
    private NFLTeam getByName(
        @RequestParam(name = "code", required = false) String code
    ) throws NoRecordException {
        return teamService.findByCode(code);
    }
}
