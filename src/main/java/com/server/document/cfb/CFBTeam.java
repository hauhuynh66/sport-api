package com.server.document.cfb;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Document(collection = "cfb_team")
@Data
public class CFBTeam {
    @Id
    @JsonIgnore
    private String id;

    @Indexed
    private String code;

    @Indexed
    private String school;

    private String nickname;

    private String city;

    private String state;

    private String conference;

    private List<String> colors;

    public CFBTeam(String school, String code, String nickname, String city, String state, String conference, List<String> colors) {
        this.school = school;
        this.code = code;
        this.nickname = nickname;
        this.city = city;
        this.state = state;
        this.conference = conference;
        this.colors = colors;
    }
}
