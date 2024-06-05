package com.server.controller.api.nfl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.data.SportTeamApiResponse;
import com.server.document.nfl.NFLTeam;
import com.server.exception.NoRecordException;
import com.server.service.nfl.NFLTeamService;

@RestController()
@RequestMapping("/api/v1/nfl/team")
public class NFLTeamController {
    @Autowired
    private NFLTeamService teamService;
    
    @GetMapping("/info")
    public SportTeamApiResponse<NFLTeam> getTeamInfo(
        @RequestParam("code") String code
    ) throws NoRecordException {
    }
    

    @GetMapping("/list")
    public List<SportTeamApiResponse<NFLTeam>> getTeamInfo(
        @RequestParam(value = "page", required = false, defaultValue = "0") String code,
        @RequestParam(value = "cnt", required = false, defaultValue = "0") String cnt
    ) throws NoRecordException {

    }
}
