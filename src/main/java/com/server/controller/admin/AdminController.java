package com.server.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/manage")
    public String manageList() {
        return "manage-list";
    }

    // @GetMapping("/nfl/add")
    // public String nflAddTeamForm() {
    //     return "nfl/team";
    // }

    // @GetMapping("/nba/add")
    // public String nbaAddTeamForm() {
    //     return "nba/team";
    // }

    // @GetMapping("/mlb/add")
    // public String mlbAddTeamForm() {
    //     return "mlb/team";
    // }
}
