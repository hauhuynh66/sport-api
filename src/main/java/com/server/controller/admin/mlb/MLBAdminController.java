package com.server.controller.admin.mlb;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.server.data.MenuItem;
import com.server.data.SportMenuInterface;
import com.server.document.general.Encyclopedia;
import com.server.exception.NoRecordException;
import com.server.exception.QueryParamException;
import com.server.service.general.EncyclopediaService;
import com.server.service.mlb.MLBTeamService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin/mlb")
@Slf4j
public class MLBAdminController implements SportMenuInterface {
   private final static String ICON = "https://upload.wikimedia.org/wikipedia/commons/a/a6/Major_League_Baseball_logo.svg";
   private static List<MenuItem> MENU = Arrays.asList(
      new MenuItem("Teams", "teams"), 
      new MenuItem("Matches", "matches")
   );

   private final static String ENC_TYPE = "MLB";

   @Autowired
   private MLBTeamService mlbTeamService;

   @Autowired
   private EncyclopediaService encService;

   @Override
   public String teams(Model model) {
      List<Encyclopedia> entries = encService.findByTypeAndName(ENC_TYPE, "TEAM_LOGO");
      Map<String, String> logos = entries.stream().collect(
         Collectors.toMap(Encyclopedia::getCode, Encyclopedia::getValue)
      );
      model.addAttribute("logos", logos);
      model.addAttribute("teams", mlbTeamService.list());
      return "/mlb/team/list";
   }

   @Override
   public String menu(Model model) {
      model.addAttribute("icon", ICON);
      model.addAttribute("menu", MENU);
      
      return "menu";
   }
   
   public String matches(int season, Model model) throws QueryParamException {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'matches'");
   }

   @Override
   public String teamInfo(@PathVariable String code, Model model) throws NoRecordException {
      log.info(code);
      Encyclopedia enc = encService.find(ENC_TYPE, "TEAM_LOGO", code);
      model.addAttribute("logo", enc.getValue());
      model.addAttribute("info", mlbTeamService.findByCode(code));
      return "/mlb/team/info";
   }
}
