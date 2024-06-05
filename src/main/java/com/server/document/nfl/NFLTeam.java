package com.server.document.nfl;

import java.util.List;

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
    private String otherName;

    private String name;

    private String division;

    private String conference;

    private int established;

    private List<String> colors;

    public NFLTeam(String code ,String otherName, String name, String division, String conference, int established, List<String> colors) {
        this.code = code;
        this.otherName = otherName;
        this.name = name;
        this.division = division;
        this.conference = conference;
        this.established = established;
        this.colors = colors;
    }
}
