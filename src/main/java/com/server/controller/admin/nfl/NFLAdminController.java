package com.server.controller.admin.nfl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.server.exception.NoRecordException;
import com.server.model.nfl.NFLTeam;
import com.server.service.nfl.NFLMatchService;
import com.server.service.nfl.NFLTeamService;

@Controller
@RequestMapping("/admin/nfl")
public class NFLAdminController {
   private static Map<String, String> menus = Map.of("team", "Teams", "schedule", "Schedule");
   
   @Autowired
   private NFLTeamService nflTeamService;

   @Autowired
   private NFLMatchService nflMatchService;


   @GetMapping("/menu")
   public String menu(Model model) {
      model.addAttribute("menus", menus);
      return "/nfl/menu";
   }

   @GetMapping("/team")
   public String team(Model model) {
      model.addAttribute("teams", nflTeamService.findAll());
      return "/nfl/team";
   }

   @GetMapping("/schedule")
   public String schedule(Model model) {
      model.addAttribute("matches", nflMatchService.findAll());
      return "/nfl/schedule";
   }

   @GetMapping("/team/{name}")
   public String teamInfo(@PathVariable String name,Model model) throws NoRecordException {
      model.addAttribute("info", nflTeamService.findByName(name));
      return "/nfl/team-info";
   }

   @PostMapping("/team/edit")
   public String editTeam(@ModelAttribute("info") NFLTeam team, BindingResult result) throws NoRecordException {
      
      return "/nfl/team-info";
   }
}
