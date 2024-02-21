package com.server.batch;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.server.model.nfl.NFLMatch;
import com.server.model.nfl.NFLStadium;
import com.server.model.nfl.NFLTeam;
import com.server.repository.nfl.NFLMatchRepositoryImpl;
import com.server.repository.nfl.NFLStadiumRepository;
import com.server.repository.nfl.NFLTeamRepositoryImpl;
import com.server.storage.StorageService;

enum TEAM_HEADERS {
    ShortName,Name,Abbreviation,Conference,Division
}

enum MATCH_HEADERS {
    Home,Away,HomeScore,AwayScore,Stadium,Date,Week,Round,Season
}

enum STADIUM_HEADERS {
    Name,Capacity,Location,Team,Opened
}

@Component
public class NFLBatch implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(NFLBatch.class);

    @Value("${data.nfl.init}")
    private boolean init = false;

    @Autowired
    private NFLTeamRepositoryImpl nflTeamRepository;

    @Autowired
    private NFLMatchRepositoryImpl nflMatchRepository;

    @Autowired
    private NFLStadiumRepository nflStadiumRepository;

    @Autowired
    private StorageService storageService;

    @Override
    public void run(String... args) throws Exception {
        if(init) {
            nflTeamRepository.clear();
            for (NFLTeam team : getTeamList()) {
                nflTeamRepository.save(team);
            }
            nflMatchRepository.clear();
            for (NFLMatch match : getMatches()) {
                nflMatchRepository.save(match);
            }
            nflStadiumRepository.clear();
            for (NFLStadium stadium : getStadium()) {
                nflStadiumRepository.save(stadium);
            }
        }
    }

    Collection<NFLStadium> getStadium() throws IOException {
        ArrayList<NFLStadium> stadiums = new ArrayList<>();

        File file = storageService.load("nfl_stadiums.csv").toFile();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
            .setHeader(STADIUM_HEADERS.class)
            .setSkipHeaderRecord(true)
            .build();

        Iterable<CSVRecord> records = csvFormat.parse(new FileReader(file));

        for (CSVRecord record : records) {
            stadiums.add(new NFLStadium(
                record.get(STADIUM_HEADERS.Name),
                Long.parseLong(record.get(STADIUM_HEADERS.Capacity)),
                record.get(STADIUM_HEADERS.Location),
                record.get(STADIUM_HEADERS.Team),
                record.get(STADIUM_HEADERS.Opened)
            ));
        }

        return stadiums;
    }

    Collection<NFLTeam> getTeamList() throws IOException {
        ArrayList<NFLTeam> teams = new ArrayList<>();

        File file = storageService.load("nfl_teams.csv").toFile();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
            .setHeader(TEAM_HEADERS.class)
            .setSkipHeaderRecord(true)
            .build();

        Iterable<CSVRecord> records = csvFormat.parse(new FileReader(file));

        for (CSVRecord record : records) {
            teams.add(new NFLTeam(
                record.get(TEAM_HEADERS.ShortName),
                record.get(TEAM_HEADERS.Name),
                record.get(TEAM_HEADERS.Abbreviation),
                record.get(TEAM_HEADERS.Division),
                record.get(TEAM_HEADERS.Conference),
                "http://localhost:8002/nfl/logo/" + record.get(TEAM_HEADERS.ShortName) + ".svg"
            ));
        }

        return teams;
    }

    Collection<NFLMatch> getMatches() throws IOException {
        ArrayList<NFLMatch> matches = new ArrayList<>();

        File file = storageService.load("nfl_matches.csv").toFile();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
            .setHeader(MATCH_HEADERS.class)
            .setSkipHeaderRecord(true)
            .build();

        Iterable<CSVRecord> records = csvFormat.parse(new FileReader(file));

        for (CSVRecord record : records) {
            int week = 0;
            String weekStr = record.get(MATCH_HEADERS.Week);
            if(weekStr == "") {
                String round = record.get(MATCH_HEADERS.Round);
                switch (round) {
                    case "Wild Card Round":
                        week = -1;
                        break;
                    case "Divisional Round":
                        week = -2;
                        break;
                    case "Conference Championship":
                        week = -3;
                        break;
                    case "SuperBowl":
                        week = -99;
                        break;
                }
            }

            if(week == 0) {
                try {
                    week = Integer.parseInt(record.get(MATCH_HEADERS.Week));
                } catch(NumberFormatException e) {
                    continue;
                }
            }

            matches.add(new NFLMatch
            (
                record.get(MATCH_HEADERS.Home),
                record.get(MATCH_HEADERS.Away),
                Integer.parseInt(record.get(MATCH_HEADERS.HomeScore)),
                Integer.parseInt(record.get(MATCH_HEADERS.AwayScore)),
                record.get(MATCH_HEADERS.Date),
                record.get(MATCH_HEADERS.Stadium),
                week,
                record.get(MATCH_HEADERS.Season)
            ));
        }

        return matches;
    }
    
}
