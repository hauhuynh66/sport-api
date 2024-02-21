package com.server.model.nfl;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "nfl_team")
@NoArgsConstructor
@Getter
@Setter
public class NFLTeam {
    @Id
    @JsonIgnore
    private String id;
    
    @Indexed
    @JsonIgnore
    private String name;

    private String fullName;

    private String abbreviation;

    private String division;

    private String conference;

    private String established;

    private String owner;

    private String stadium;

    private String logoUrl;

    public NFLTeam(String name, String fullName, String abbreviation, String division, String conference, String logoUrl) {
        this.name = name;
        this.fullName = fullName;
        this.abbreviation = abbreviation;
        this.division = division;
        this.conference = conference;
        this.logoUrl = logoUrl;
    }
}
