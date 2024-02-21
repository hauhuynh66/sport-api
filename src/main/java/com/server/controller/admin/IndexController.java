package com.server.controller.admin;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class IndexController {
    private static Map<String, String> menus = Map.of("nfl", "NFL", "geo", "Geographic");

    @GetMapping("/menu")
    public String menu(Model model) {
        model.addAttribute("menus", menus);
        return "menu";
    }
}
