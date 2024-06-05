package com.server.document.nfl;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document("nfl_player")
@NoArgsConstructor
@Data
public class NFLPlayer {
    @Id
    private String id;

    @Indexed
    private String name;

    @Indexed
    private String team;

    @Indexed
    private String position;
    
    @Indexed
    private String number;

    private String height;

    private int weight;

    private String birthYear;

    private String joinYear;

    private String college;

    public NFLPlayer(String team, String name, String position, String number, String weight, String height, String college) {
        this.name = name;
        this.team = team;
        this.position = position;
        this.height = height.replace("-", ",");
        try {
            this.number = Integer.parseInt(number) + "";
            this.weight = Integer.parseInt(weight);
        } catch (NumberFormatException e) {
            this.number = "-";
            this.weight = 0;
        }

        this.college = college;
    }
}
