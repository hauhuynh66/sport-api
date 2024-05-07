package com.server.controller.admin.mlb;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.server.service.general.ImageUrlService;
import com.server.service.mlb.MLBTeamService;

@Controller
@RequestMapping("/admin/sport/mlb")
public class MLBAdminController {
   private final static String iconPath = "https://upload.wikimedia.org/wikipedia/commons/a/a6/Major_League_Baseball_logo.svg";
   private static Map<String, String> menus = Map.of("team", "Teams", "schedule", "Schedule");

   @Autowired
   private MLBTeamService mlbTeamService;

   @Autowired
   private ImageUrlService urlService;


   @GetMapping("/menu")
   public String menu(Model model) {
      model.addAttribute("icon", iconPath);
      model.addAttribute("menus", menus);
      return "/mlb/menu";
   }

   @GetMapping("/team")
   public String team(Model model) {
      model.addAttribute("logos", urlService.findByTypeAndName("MLB", "team_logo"));
      model.addAttribute("teams", mlbTeamService.findAll());
      return "/mlb/team/list";
   }

   @GetMapping("/schedule")
   public String schedule(Model model) {
      return "/mlb/schedule/list";
   }
}
