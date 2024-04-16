package com.server.document.nfl;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document("nfl_record")
public class NFLRecord {
    @Id
    @JsonIgnore
    private String id;

    @Indexed
    @JsonIgnore
    private String team;

    private int wins;

    private int losses;

    private int ties;

    private String division;

    private String conference;

    private int pointsFor;
    private int pointsAgainst;
    private String streak;
    private int season;

    public NFLRecord(int season ,String team, int wins, int losses, int ties) {
        this.season = season;
        this.team = team;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }
}
