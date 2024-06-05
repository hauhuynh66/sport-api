package com.server.controller.admin.nba;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.server.data.MenuItem;
import com.server.data.SportMenuInterface;
import com.server.exception.NoRecordException;
import com.server.exception.QueryParamException;
import com.server.service.general.EncyclopediaService;

@Controller
@RequestMapping("/admin/nba")
public class NBAAdminController implements SportMenuInterface {
   private static String ICON = "https://upload.wikimedia.org/wikipedia/en/0/03/National_Basketball_Association_logo.svg";
   private static List<MenuItem> MENU = Arrays.asList(
      new MenuItem("Teams", "teams"), 
      new MenuItem("Matches", "matches")
   );

   @Autowired
   private EncyclopediaService urlService;

   @GetMapping("/menu")
   public String menu(Model model) {
      model.addAttribute("icon", ICON);
      model.addAttribute("menu", MENU);
      
      return "menu";
   }

   @Override
   public String teams(Model model) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'teams'");
   }


   @Override
   public String matches(int season, Model model) throws QueryParamException {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'matches'");
   }


   @Override
   public String teamInfo(String code, Model model) throws NoRecordException {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'teamInfo'");
   }
}
