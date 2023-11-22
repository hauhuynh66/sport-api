package com.server.model.nfl;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NFLTeam {
    private String name;

    private String abbreviation;

    private String division;

    private String conference;

    private String logoUrl;
}
