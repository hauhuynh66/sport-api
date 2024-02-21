package com.server.controller.admin.geo;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/geo")
public class GeoAdminController {
    private static Map<String, String> menus = Map.of();

   @GetMapping("/menu")
   public String menu(Model model) {
      model.addAttribute("menus", menus);
      return "/geo/menu";
   }
}
