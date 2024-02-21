package com.server.controller.admin.nfl;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/nfl")
public class NFLAdminController {
   private static Map<String, String> menus = Map.of("team", "Teams", "schedule", "Schedule");

   @GetMapping("/menu")
   public String menu(Model model) {
      model.addAttribute("menus", menus);
      return "/nfl/menu";
   }

   @GetMapping("/team")
   public String team(Model model) {
      return "/nfl/team";
   }

   @GetMapping("/schedule")
   public String schedule(Model model) {
      model.addAttribute("menus", menus);
      return "/nfl/schedule";
   }
}
