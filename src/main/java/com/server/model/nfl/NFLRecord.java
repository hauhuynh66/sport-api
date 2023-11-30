package com.server.model.nfl;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@NoArgsConstructor
@Getter
@Setter
public class NFLRecord {
    @Id
    private String id;
    
    private NFLTeam team;
    
    private String season;

    private int wins;

    private int draws = 0;

    private int loses;

    public NFLRecord(NFLTeam team, String season, int w, int l) {
        this.team = team;
        this.season = season;
        this.wins = w;
        this.loses = l;
    }

    public NFLRecord(NFLTeam team, String season, int w, int l, int d) {
        this(team, season, w, l);
        this.draws = d;
    }
}
