package com.server.model.nfl;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Document("nfl_match")
@Getter
@Setter
public class NFLMatch {
    @Id
    private String id;

    private String homeTeam;
    private String awayTeam;

    private int homeScore;
    private int awayScore;

    private String date;
    private int week;
    private String season;
    private String stadium;

    public NFLMatch(String home, String away, int homeScore, int awayScore, String date, String stadium, int week, String season) {
        this.homeTeam = home;
        this.awayTeam = away;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.date = date;
        this.stadium = stadium;
        this.week = week;
        this.season = season;
    }
}
