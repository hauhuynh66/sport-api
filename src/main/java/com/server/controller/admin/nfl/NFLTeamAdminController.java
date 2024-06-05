package com.server.controller.admin.nfl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.server.data.MenuItem;
import com.server.data.SportMenuInterface;
import com.server.document.general.Encyclopedia;
import com.server.exception.NoRecordException;
import com.server.exception.QueryParamException;
import com.server.service.general.EncyclopediaService;
import com.server.service.nfl.NFLMatchService;
import com.server.service.nfl.NFLPlayerService;
import com.server.service.nfl.NFLTeamService;

@Controller
@RequestMapping("/admin/nfl")
public class NFLTeamAdminController implements SportMenuInterface {
   private static String ICON = "https://upload.wikimedia.org/wikipedia/en/a/a2/National_Football_League_logo.svg";
   private static List<MenuItem> MENU = Arrays.asList(
      new MenuItem("Teams", "teams"),
      new MenuItem("Matches", "matches")
   );
   private static String ENC_TYPE = "NFL";
   
   @Autowired
   private NFLTeamService nflTeamService;

   @Autowired
   private NFLPlayerService nflPlayerService;

   @Autowired
   private NFLMatchService nflMatchService;

   @Autowired
   private EncyclopediaService encService;

   @Override
   public String menu(Model model) {
      model.addAttribute("icon", ICON);
      model.addAttribute("menu", MENU);
      
      return "menu";
   }

   @Override
   public String teams(Model model) {
      List<Encyclopedia> entries = encService.findByTypeAndName(ENC_TYPE, "TEAM_LOGO");
      Map<String, String> logos = entries.stream().collect(Collectors.toMap(Encyclopedia::getCode, Encyclopedia::getValue));
      model.addAttribute("logos", logos);
      model.addAttribute("teams", nflTeamService.findAll());
      return "/nfl/team/list";
   }

   @Override
   public String matches(@RequestParam(name = "season", required = false, defaultValue = "0") int season ,Model model) throws QueryParamException {
      int currentYear = Calendar.getInstance().get(Calendar.YEAR);
      if(season > currentYear) {
         throw new QueryParamException(season + " season has not yet been started");
      }

      if(season == 0) {
         season = currentYear;
      }

      List<Encyclopedia> encs = encService.findByTypeAndName(ENC_TYPE, "TEAM_LOGO");
      Map<String, String> logo = encs.stream().collect(
         Collectors.toMap(Encyclopedia::getCode, Encyclopedia::getValue)
      );
      model.addAttribute("season", season);
      model.addAttribute("logos", logo);
      model.addAttribute("season_list", IntStream.range(currentYear - 10, currentYear + 1).toArray());
      model.addAttribute("matches", nflMatchService.findBySeason(season));
      return "/nfl/match/list";
   }

   @Override
   public String teamInfo(@PathVariable String code, Model model) throws NoRecordException {
      Encyclopedia enc = encService.find(ENC_TYPE, "TEAM_LOGO",code);
      model.addAttribute("logo", enc.getValue());
      model.addAttribute("info", nflTeamService.findByCode(code));
      model.addAttribute("roster", nflPlayerService.findByTeam(code));
      return "/nfl/team/info";
   }
}
