package com.server.document.nfl;

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
    private String code;

    @JsonIgnore
    private String name;

    private String fullName;

    private String abbreviation;

    private String division;

    private String conference;

    private int established;

    public NFLTeam(String code ,String name, String fullName, String abbreviation, String division, String conference, int established) {
        this.code = code;
        this.name = name;
        this.fullName = fullName;
        this.abbreviation = abbreviation;
        this.division = division;
        this.conference = conference;
        this.established = established;
    }
}
