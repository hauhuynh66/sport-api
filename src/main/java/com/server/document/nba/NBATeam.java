package com.server.document.nba;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Document(collection = "nba_team")
@Data
public class NBATeam {
    @Id
    @JsonIgnore
    private String id;

    @Indexed
    @JsonIgnore
    private String name;

    private String conference;

    private String division;

    private String location;

    private String arena;

    private int founded;

    public NBATeam(String name, String division, String conference, String location, String arena, int founded) {
        this.name = name;
        this.division = division;
        this.conference = conference;
        this.location = location;
        this.arena = arena;
        this.founded = founded;
    }
}
