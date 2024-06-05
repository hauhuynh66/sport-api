package com.server.controller.admin.nfl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/nfl/match")
public class NFLMatchAdminController {
    // @Autowired
    // private NFLTeamService nflTeamService;

    // @Autowired
    // private NFLMatchService nflMatchService;

    // @Autowired
    // private EncyclopediaService urlService;
    
    // private static String ENC_TYPE = "NFL";

    // @GetMapping("/matches/form")
    // public String scheduleForm(ModelMap model) {
    //     List<Pair<String, String>> teamList = nflTeamService.teamNames();
    //     if(!model.containsAttribute("match")) {
    //         model.addAttribute("match", new NFLMatch());
    //     }

    //     model.addAttribute("teams", teamList);
    //     model.addAttribute("rounds", NFLMatchService.ROUNDS);

    //     return "/nfl/match/form";
    // }

    // @PostMapping("/matches/check")
    // public String scheduleFormCheck(@Valid NFLMatch match, BindingResult result, Model model, RedirectAttributes redirectAttrs) {
    //     List<NFLMatchError> validateErrors = nflMatchService.validate(match);
    //     if(result.hasErrors() || !validateErrors.isEmpty()) {
    //         redirectAttrs.addFlashAttribute("match", match);
    //         redirectAttrs.addFlashAttribute("errors", result.getAllErrors());
    //         redirectAttrs.addFlashAttribute("validate_errors", validateErrors);
    //         return "redirect:/admin/nfl/match/form";
    //     }

    //     model.addAttribute("match", match);
    //     model.addAttribute("status", nflMatchService.getMatchStatus(match));
    //     model.addAttribute("team1_logo", urlService.find(ENC_TYPE, "TEAM_LOGO", match.getTeamOne()));
    //     model.addAttribute("team2_logo", urlService.find(ENC_TYPE, "TEAM_LOGO", match.getTeamTwo()));
    //     model.addAttribute("round", NFLMatchService.ROUNDS.get(match.getRound()));
    //     return "/nfl/match/confirm";
    // }

    // @PostMapping("/matches/add")
    // public String scheduleAdd(@Valid NFLMatch match, BindingResult result, Model model) {
    //     this.nflMatchService.save(match);

    //     return "redirect:/admin/nfl/matches";
    // }

    // @GetMapping("/matches/upload")
    // public String scheduleUploadForm(ModelMap model) {
    //     return "/nfl/match/upload";
    // }
}