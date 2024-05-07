package com.server.controller.admin;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.server.lib.SortUtils;
import com.server.service.general.ImageUrlService;

@Controller
@RequestMapping("/admin")
public class IndexController {
    private final static String iconPath = "/img/cogs.svg";
    private static Map<String, String> menus = Map.of(
        "sport/menu", "Sports",
        "geo/menu", "Geographic",
        "dict/menu", "Dictionary"
    );

    private static Map<String, String> sport_menu = Map.of(
        "nfl/menu", "NFL",
        "mlb/menu", "MLB",
        "nba/menu", "NBA",
        "cfb/menu","CFB"
    );

    @Autowired
    private ImageUrlService urlService;
    
    @GetMapping("/menu")
    public String menu(Model model) {
        model.addAttribute("icon", iconPath);
        model.addAttribute("keys", SortUtils.getSortedMapKey(menus));
        model.addAttribute("menus", menus);
        return "menu";
    }

    @GetMapping("/sport/menu")
    public String sportMenu(Model model) {
        model.addAttribute("icon", iconPath);
        model.addAttribute("keys", SortUtils.getSortedMapKey(sport_menu));
        model.addAttribute("menus", sport_menu);
        return "menu";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        return "login";
    }

    @GetMapping("/images")
    public String imageList(@RequestParam(name="type", required = false, defaultValue = "MLB") String type, Model model) {
        List<String> types = Arrays.asList("MLB", "NFL");
        model.addAttribute("types", types);
        model.addAttribute("urls", urlService.findByType(type));
        return "list";
    }
}
