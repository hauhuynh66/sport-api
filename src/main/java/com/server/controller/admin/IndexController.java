package com.server.controller.admin;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.server.data.MenuItem;

@Controller
@RequestMapping("/admin")
public class IndexController {
    private final static String ICON = "/img/cogs.svg";

    private static List<MenuItem> MENU = Arrays.asList(
        new MenuItem("CFB", "cfb/menu"),
        new MenuItem("NFL", "nfl/menu"),
        new MenuItem("NBA", "nba/menu"),
        new MenuItem("MLB", "mlb/menu"),
        new MenuItem("Encyclopedia", "enc/list")
    );

    @GetMapping("/menu")
    public String sportMenu(Model model) {
        model.addAttribute("icon", ICON);
        model.addAttribute("menu", MENU);
        return "menu";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        return "login";
    }
}
