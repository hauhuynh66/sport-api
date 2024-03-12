package com.server.controller.admin.nfl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.server.exception.NoRecordException;
import com.server.service.nfl.NFLMatchService;
import com.server.service.nfl.NFLTeamService;

@Controller
@RequestMapping("/admin/nfl")
public class NFLAdminController {
   private final static String iconPath = "/admin/img/nfl.svg";
   private static Map<String, String> menus = Map.of("team", "Teams", "schedule", "Schedule");
   
   @Autowired
   private NFLTeamService nflTeamService;

   @Autowired
   private NFLMatchService nflMatchService;


   @GetMapping("/menu")
   public String menu(Model model) {
      model.addAttribute("icon", iconPath);
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
      model.addAttribute("logos", nflTeamService.getLogoList());
      model.addAttribute("matches", nflMatchService.findAll());
      return "/nfl/schedule";
   }

   @GetMapping("/team/{name}")
   public String teamInfo(@PathVariable String name, Model model) throws NoRecordException {
      model.addAttribute("info", nflTeamService.findByName(name));
      return "/nfl/info";
   }

   @GetMapping("/schedule/add")
   public String scheduleAddForm(Model model) throws NoRecordException {
      model.addAttribute("mode", 2);
      model.addAttribute("dropdown", nflTeamService.getTeamNames());
      return "/nfl/schedule_form";
   }

   @GetMapping("/schedule/info")
   public String scheduleInfo(@RequestParam(name="id") String id, Model model) throws NoRecordException {
      model.addAttribute("mode", 1);
      model.addAttribute("match", nflMatchService.findById(id));
      return "/nfl/schedule_form";
   }
}
