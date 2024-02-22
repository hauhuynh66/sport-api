package com.server.controller.admin.dict;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/dict")
public class DictionaryAdminController {
    private static Map<String, String> menus = Map.of("eng", "English", "jpn", "Japanese");
    
    @GetMapping("/menu")
    public String menu(Model model) {
        model.addAttribute("menus", menus);
        return "/dict/menu";
    }

}
