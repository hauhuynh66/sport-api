package com.server.service.nfl;

public enum NFLMatchError {
    SeasonError("Can only schedule a match for next season"),
    SameTeamError("Must select 2 difference teams"),
    ScoreError("Ended match score is not valid");

    public String message;
    NFLMatchError(String message) {
        this.message = message;
    }
}