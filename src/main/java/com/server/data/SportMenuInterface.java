package com.server.data;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.server.exception.NoRecordException;
import com.server.exception.QueryParamException;

public interface SportMenuInterface {
    @GetMapping("/menu")
    String menu(Model model);
    @GetMapping("/teams")
    String teams(Model model);
    @GetMapping("/matches")
    String matches(int season, Model model) throws QueryParamException;
    @GetMapping("/team/{code}")
    String teamInfo(@PathVariable String code, Model model) throws NoRecordException;
}
