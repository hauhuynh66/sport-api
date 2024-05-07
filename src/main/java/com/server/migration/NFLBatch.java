package com.server.migration;

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

import com.server.document.general.ImageUrl;
import com.server.document.nfl.NFLMatch;
import com.server.document.nfl.NFLTeam;
import com.server.repository.general.ImageUrlRepository;
import com.server.repository.nfl.NFLMatchRepositoryImpl;
import com.server.repository.nfl.NFLTeamRepositoryImpl;
import com.server.storage.StorageService;

enum NFL_TEAM_CSV_HEADERS {
    Code, ShortName, Name, Abbreviation, Conference, Division, Established
}

enum NFL_MATCH_CSV_HEADERS {
    Home, Away, HomeScore, AwayScore, Stadium, Round, Season
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
    private ImageUrlRepository imageUrlRepository;

    @Autowired
    private StorageService storageService;

    @Override
    public void run(String... args) throws Exception {
        if(init) {
            for (NFLTeam team : getTeamList()) {
                nflTeamRepository.save(team);
            }

            for (NFLMatch match : getMatches()) {
                nflMatchRepository.save(match);
            }
        }
    }

    Collection<NFLTeam> getTeamList() throws IOException {
        ArrayList<NFLTeam> teams = new ArrayList<>();

        File file = storageService.load("nfl/teams.csv").toFile();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
            .setHeader(NFL_TEAM_CSV_HEADERS.class)
            .setSkipHeaderRecord(true)
            .build();

        Iterable<CSVRecord> records = csvFormat.parse(new FileReader(file));

        for (CSVRecord record : records) {
            teams.add(new NFLTeam(
                record.get(NFL_TEAM_CSV_HEADERS.Code),
                record.get(NFL_TEAM_CSV_HEADERS.ShortName),
                record.get(NFL_TEAM_CSV_HEADERS.Name),
                record.get(NFL_TEAM_CSV_HEADERS.Abbreviation),
                record.get(NFL_TEAM_CSV_HEADERS.Division),
                record.get(NFL_TEAM_CSV_HEADERS.Conference),
                Integer.parseInt(record.get(NFL_TEAM_CSV_HEADERS.Established))
            ));
            imageUrlRepository.save(
                new ImageUrl(
                    record.get(NFL_TEAM_CSV_HEADERS.Code),
                    "NFL",
                    "TEAM_LOGO",
                    "http://localhost:8002/nfl/logo/" + record.get(NFL_TEAM_CSV_HEADERS.ShortName) + ".svg"
                )
            );
        }

        return teams;
    }

    Collection<NFLMatch> getMatches() throws IOException {
        ArrayList<NFLMatch> matches = new ArrayList<>();

        File file = storageService.load("nfl/matches.csv").toFile();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
            .setHeader(NFL_MATCH_CSV_HEADERS.class)
            .setSkipHeaderRecord(true)
            .build();

        Iterable<CSVRecord> records = csvFormat.parse(new FileReader(file));

        for (CSVRecord record : records) {
            matches.add(new NFLMatch
            (
                record.get(NFL_MATCH_CSV_HEADERS.Home),
                record.get(NFL_MATCH_CSV_HEADERS.Away),
                Integer.parseInt(record.get(NFL_MATCH_CSV_HEADERS.HomeScore)),
                Integer.parseInt(record.get(NFL_MATCH_CSV_HEADERS.AwayScore)),
                record.get(NFL_MATCH_CSV_HEADERS.Stadium),
                Integer.parseInt(record.get(NFL_MATCH_CSV_HEADERS.Round)),
                Integer.parseInt(record.get(NFL_MATCH_CSV_HEADERS.Season))
            ));
        }

        return matches;
    }
    
}
