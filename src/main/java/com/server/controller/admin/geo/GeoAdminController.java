package com.server.controller.admin.geo;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/geo")
public class GeoAdminController {
   private final static String iconPath = "/img/globe.svg";
   private static Map<String, String> menus = Map.of("countries", "Countries", "cities", "Cities");

   @GetMapping("/menu")
   public String menu(Model model) {
      model.addAttribute("icon", iconPath);
      model.addAttribute("menus", menus);
      return "/geo/menu";
   }

   @GetMapping("/cities")
   public String cities(Model model) {
      model.addAttribute("menus", menus);
      return "/geo/city";
   }

   @GetMapping("/countries")
   public String clountries(Model model) {
      model.addAttribute("menus", menus);
      return "/geo/country";
   }
}
