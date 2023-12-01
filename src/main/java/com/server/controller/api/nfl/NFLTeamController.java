package com.server.controller.api.nfl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.model.nfl.NFLTeam;
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

}
