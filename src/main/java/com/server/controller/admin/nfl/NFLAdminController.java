package com.server.controller.admin.nfl;

import java.util.Calendar;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.server.document.nfl.NFLMatch;
import com.server.exception.NoRecordException;
import com.server.exception.QueryParamException;
import com.server.lib.SortUtils;
import com.server.service.general.ImageUrlService;
import com.server.service.nfl.NFLMatchService;
import com.server.service.nfl.NFLTeamService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/nfl")
public class NFLAdminController {
   private final static String iconPath = "/img/nfl.svg";
   private static Map<String, String> menus = Map.of("team", "Teams", "schedule", "Schedule");
   private static Map<Integer, String> rounds = Map.ofEntries(
      Map.entry(1, "Week 1"),
      Map.entry(2, "Week 2"),
      Map.entry(3, "Week 3"),
      Map.entry(4, "Week 4"),
      Map.entry(5, "Week 5"),
      Map.entry(6, "Week 6"),
      Map.entry(7, "Week 7"),
      Map.entry(8, "Week 8"),
      Map.entry(9, "Week 9"),
      Map.entry(10, "Week 10"),
      Map.entry(11, "Week 11"),
      Map.entry(12, "Week 12"),
      Map.entry(13, "Week 13"),
      Map.entry(14, "Week 14"),
      Map.entry(15, "Week 15")
   );
   
   @Autowired
   private NFLTeamService nflTeamService;

   @Autowired
   private ImageUrlService urlService;

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
      model.addAttribute("logos", urlService.findByType("NFL"));
      model.addAttribute("teams", nflTeamService.findAll());
      return "/nfl/team";
   }

   @GetMapping("/schedule")
   public String schedule(@RequestParam(name = "season", required = false, defaultValue = "0") int season ,Model model) throws QueryParamException {
      int currentYear = Calendar.getInstance().get(Calendar.YEAR);
      if(season > currentYear) {
         throw new QueryParamException(season + " season has not yet been started");
      }

      model.addAttribute("selected", season);
      model.addAttribute("logos", urlService.findByType("NFL"));
      model.addAttribute("season_list", IntStream.range(currentYear - 10, currentYear).toArray());
      model.addAttribute("matches", nflMatchService.findBySeason(season));
      return "/nfl/schedule/list";
   }

   @GetMapping("/team/{code}")
   public String teamInfo(@PathVariable String code, Model model) throws NoRecordException {
      model.addAttribute("logo", urlService.findByTypeAndCode("NFL", code));
      model.addAttribute("info", nflTeamService.findByCode(code));
      return "/nfl/info";
   }

   @GetMapping("/schedule/form")
   public String scheduleForm(NFLMatch match, BindingResult result, ModelMap model) {
      Map<String, String> teamList = nflTeamService.getTeamNames();
      if(!model.containsAttribute("match")) {
         model.addAttribute("match", match);
      }
      model.addAttribute("sorted_teams", SortUtils.getSortedMapKey(teamList));
      model.addAttribute("teams", teamList);
      model.addAttribute("sorted_rounds", SortUtils.getSortedMapKey(rounds));
      model.addAttribute("rounds", rounds);

      return "/nfl/schedule/form";
   }

   @PostMapping("/schedule/check")
   public String scheduleFormCheck(@Valid NFLMatch match, BindingResult result, RedirectAttributes redirectAttrs) {
      if(result.hasErrors()) {
         redirectAttrs.addFlashAttribute("match", match);
         redirectAttrs.addFlashAttribute("errors", result.getAllErrors());
         return "redirect:/admin/nfl/schedule/form";
      }

      return "/nfl/schedule/confirm";
   }
}
