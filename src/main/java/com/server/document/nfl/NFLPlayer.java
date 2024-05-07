package com.server.document.nfl;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document("nfl_player")
@Data
public class NFLPlayer {
    @Id
    private String id;

    private String name;

    private String team;

    private String position;

    private int number;

    private float height;

    private int weight;

    private int age;

    private int yoe;

    private String college;

    NFLPlayer(String name, String team, String position, int number, float height, int weight, int age, int yoe, String college) {
        this.name = name;
        this.team = team;
        this.position = position;
        this.number = number;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.yoe = yoe;
        this.college = college;
    }
}
