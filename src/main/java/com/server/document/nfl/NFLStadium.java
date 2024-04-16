package com.server.document.nfl;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "nfl_stadium")
@Getter
@Setter
public class NFLStadium {
    @Id
    private String id;

    private String name;

    private long capacity;
    
    private String location;

    private List<String> team;

    private String opened;

    public NFLStadium(String name, long capacity, String location, List<String> team, String opened) {
        this.name = name;
        this.capacity = capacity;
        this.location = location;
        this.team = team;
        this.opened = opened;
    }
}
