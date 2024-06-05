package com.server.controller.admin.cfb;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.server.data.MenuItem;
import com.server.data.SportMenuInterface;
import com.server.document.general.Encyclopedia;
import com.server.exception.NoRecordException;
import com.server.exception.QueryParamException;
import com.server.service.cfb.CFBTeamService;
import com.server.service.general.EncyclopediaService;

@Controller
@RequestMapping("/admin/cfb")
public class CFBAdminController implements SportMenuInterface {
   private final static String ICON = "https://upload.wikimedia.org/wikipedia/en/c/cf/NCAA_football_icon_logo.svg";
   private static List<MenuItem> MENU = Arrays.asList(
      new MenuItem("Teams", "teams"), 
      new MenuItem("Matches", "matches")
   );

   @Autowired
   private CFBTeamService cfbTeamService;

   @Autowired
   private EncyclopediaService encService;

   @GetMapping("/menu")
   public String menu(Model model) {
      model.addAttribute("icon", ICON);
      model.addAttribute("menu", MENU);
      
      return "menu";
   }
   
   @Override
   public String teams(Model model) {
      List<Encyclopedia> encs = encService.findByTypeAndName("CFB", "TEAM_LOGO");
      Map<String, String> logos = encs.stream().collect(
         Collectors.toMap(Encyclopedia::getCode, Encyclopedia::getValue)
      );
      model.addAttribute("logos", logos);
      model.addAttribute("teams", cfbTeamService.findAll());
      return "/cfb/team/list";
   }


   @Override
   public String matches(int season, Model model) throws QueryParamException {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'matches'");
   }


   @Override
   public String teamInfo(String code, Model model) throws NoRecordException {
      Encyclopedia enc = encService.find("CFB", "TEAM_LOGO", code);
      model.addAttribute("logo", enc.getValue());
      model.addAttribute("info", cfbTeamService.findByCode(code));
      return "/cfb/team/info";
   }
}
