package com.server.controller.admin.cfb;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.server.service.general.ImageUrlService;

@Controller
@RequestMapping("/admin/sport/cfb")
public class CFBAdminController {
    private final static String iconPath = "https://upload.wikimedia.org/wikipedia/en/c/cf/NCAA_football_icon_logo.svg";
   private static Map<String, String> menus = Map.of("team", "Teams", "schedule", "Schedule");

   @Autowired
   private ImageUrlService urlService;


   @GetMapping("/menu")
   public String menu(Model model) {
      model.addAttribute("icon", iconPath);
      model.addAttribute("menus", menus);
      return "/cfb/menu";
   }
}
