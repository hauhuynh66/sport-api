package com.server.controller.api.nfl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/nfl/v1/team")
public class NFLTeamController {
    @GetMapping("/{name}")
    private String teamInfo(@PathVariable String name) {
        return name;
    }
}
