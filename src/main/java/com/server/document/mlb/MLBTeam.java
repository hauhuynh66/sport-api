package com.server.document.mlb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "mlb_team")
@NoArgsConstructor
@Getter
@Setter
public class MLBTeam {
    @Id
    @JsonIgnore
    private String id;

    @Indexed
    @JsonIgnore
    private String code;

    @JsonIgnore
    private String name;

    private String division;

    private String league;

    private int established;

    public MLBTeam(String code,String name, String division, String league, int founded) {
        this.code = code;
        this.name = name;
        this.division = division;
        this.league = league;
        this.established = founded;
    }
}
