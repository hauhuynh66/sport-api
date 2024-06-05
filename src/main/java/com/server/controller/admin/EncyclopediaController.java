package com.server.controller.admin;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.server.document.general.Encyclopedia;
import com.server.exception.DBException;
import com.server.service.general.EncyclopediaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/enc")
public class EncyclopediaController {
    @Autowired
    private EncyclopediaService encyclopediaService;

    List<String> types = Arrays.asList("NCAA", "NBA", "NFL", "MLB");

    @GetMapping("/list")
    public String urlList(@RequestParam(name = "type", required = false, defaultValue = "MLB") String type,
            Model model) {
        model.addAttribute("selected", type);
        model.addAttribute("types", types);
        model.addAttribute("encs", encyclopediaService.findByType(type));
        return "/enc/list";
    }

    @GetMapping("/form")
    public String encForm(
            @RequestParam(name = "type") String type,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "code") String code,
            ModelMap model) {
        Encyclopedia enc = encyclopediaService.find(type.toUpperCase(), name.toUpperCase(), code.toUpperCase());
        model.addAttribute("mode", "form");
        model.addAttribute("enc", enc);
        return "/enc/form";
    }

    @PostMapping("/update")
    public String encUpdate(@Valid Encyclopedia enc, BindingResult bindingResult, Model model) throws Exception {
        String id = encyclopediaService.save(enc);
        model.addAttribute("list_url", "/admin/enc/list?type=" + enc.getType());
        if (id != null) {
            model.addAttribute("enc", enc);
            model.addAttribute("mode", "thanks");
            return "/enc/form";
        } else {
            throw new DBException("An error have occured");
        }
    }
}
