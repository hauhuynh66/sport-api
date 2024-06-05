package com.server.document.nfl;

import java.security.InvalidParameterException;
import java.util.Calendar;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.server.document.validator.NFLScore;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("nfl_match")
@NoArgsConstructor
@Getter
@Setter
public class NFLMatch {
    @Id
    private String id;

    @NotNull
    @Size(min = 2, max = 3)
    private String teamOne;

    @NotNull
    @Size(min = 2, max = 3)
    private String teamTwo;
    
    @NotNull
    @NFLScore
    private int scoreOne = 0;

    @NotNull
    @NFLScore
    private int scoreTwo = 0;

    @NotNull
    @Min(1)
    @Max(19)
    private int round = 1;

    @NotNull
    private int season = Calendar.getInstance().get(Calendar.YEAR);

    public NFLMatch(String teamOne, String teamTwo, int scoreOne, int scoreTwo, int round, int season) throws InvalidParameterException {
        if(teamOne == teamTwo) {
            throw new InvalidParameterException("Same team cant face eachother");
        }

        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        this.scoreOne = scoreOne;
        this.scoreTwo = scoreTwo;
        this.round = round;
        this.season = season;
    }
}
