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

    private Integer homeScore;
    private Integer awayScore;

    private String date;
}
