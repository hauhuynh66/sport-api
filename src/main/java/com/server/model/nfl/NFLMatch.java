package com.server.model.nfl;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

    public NFLMatch(String homeTeam, String awayTeam, int homeScore, int awayScore, String date, String stadium, int week, String season) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.date = date;
        this.stadium = stadium;
        this.week = week;
        this.season = season;
    }
}
