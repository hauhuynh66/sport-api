package com.server.document.nfl;

import java.util.Calendar;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("nfl_match")
@NoArgsConstructor
@Getter
@Setter
public class NFLMatch {
    @Id
    private String id;

    @NotNull
    @Size(min = 2, max = 3)
    private String teamOne;
    @NotNull
    @Size(min = 2, max = 3)
    private String teamTwo;
    
    @NotNull
    private int scoreOne = 0;

    @NotNull
    private int scoreTwo = 0;

    @NotNull
    private String date;

    @NotNull
    private int round = 1;

    @NotNull
    private int season = Calendar.getInstance().get(Calendar.YEAR) - 1;

    private String stadium;

    public NFLMatch(String teamOne, String teamTwo, int scoreOne, int scoreTwo, String date, String stadium, int round, int season) {
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        this.scoreOne = scoreOne;
        this.scoreTwo = scoreTwo;
        this.date = date;
        this.stadium = stadium;
        this.round = round;
        this.season = season;
    }
}
