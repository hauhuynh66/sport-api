package com.server.migration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.server.document.cfb.CFBTeam;
import com.server.document.general.Encyclopedia;
import com.server.document.mlb.MLBTeam;
import com.server.document.nfl.NFLMatch;
import com.server.document.nfl.NFLPlayer;
import com.server.document.nfl.NFLTeam;
import com.server.service.cfb.CFBTeamService;
import com.server.service.general.EncyclopediaService;
import com.server.service.mlb.MLBTeamService;
import com.server.service.nfl.NFLMatchService;
import com.server.service.nfl.NFLPlayerService;
import com.server.service.nfl.NFLTeamService;
import com.server.storage.StorageService;

enum NFL_TEAM_CSV_HEADERS {
    Code, ShortName, Name, Conference, Division, Established, WebSite, LogoURL, Colors
}

enum NFL_MATCH_CSV_HEADERS {
    Team1, Team2, Score1, Score2, Round, Season
}

enum NFL_PLAYER_CSV_HEADERS {
    Name, Number, Position, Height, Weight, Age, YearsOfExperience, College
}

enum NFL_HEADCOACH_CSV_HEADERS {
    TeamName, Name
}

enum MLB_TEAM_CSV_HEADERS {
    League, Division, Team, City, Founded, Code, LogoURL
}

enum NCAA_TEAM_CSV_HEADERS {
    School, Nickname, City, State, Conference, Code, LogoURL, Colors
}



@Component
public class SportMigration implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(SportMigration.class);

    @Value("${data.sport.migrate}")
    private boolean migrate = false;

    @Autowired
    private NFLTeamService nflTeamService;

    @Autowired
    private NFLMatchService nflMatchService;

    @Autowired
    private NFLPlayerService nflPlayerService;

    @Autowired
    private MLBTeamService mlbTeamService;

    @Autowired
    private CFBTeamService cfbTeamService;

    @Autowired
    private EncyclopediaService encService;

    @Autowired
    private StorageService storageService;


    @Override
    public void run(String... args) throws Exception {
        if(migrate) {
            migrateNFLTeams();
            migrateNFLMatches();
            migrateMLBTeams();
            migrateCFBTeams();

            migrateNFLPlayers();
        }
    }

    void migrateNFLTeams() throws IOException {
        File file = storageService.load("nfl/teams.csv").toFile();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
            .setHeader(NFL_TEAM_CSV_HEADERS.class)
            .setSkipHeaderRecord(true)
            .build();

        Iterable<CSVRecord> records = csvFormat.parse(new FileReader(file));
        nflTeamService.clear();
        for (CSVRecord record : records) {
            String code = record.get(NFL_TEAM_CSV_HEADERS.Code);
            String name = record.get(NFL_TEAM_CSV_HEADERS.ShortName);
            nflTeamService.save(new NFLTeam(
                code, name,
                record.get(NFL_TEAM_CSV_HEADERS.Name),
                record.get(NFL_TEAM_CSV_HEADERS.Division),
                record.get(NFL_TEAM_CSV_HEADERS.Conference),
                Integer.parseInt(record.get(NFL_TEAM_CSV_HEADERS.Established)),
                Arrays.asList(record.get(NFL_TEAM_CSV_HEADERS.Colors).split(";"))
            )); 
            
            encService.save(new Encyclopedia(
                record.get(NFL_TEAM_CSV_HEADERS.Code),
                "NFL",
                "TEAM_LOGO",
                record.get(NFL_TEAM_CSV_HEADERS.LogoURL)
            ));
            encService.save(new Encyclopedia(
                record.get(NFL_TEAM_CSV_HEADERS.Code),
                "NFL",
                "SITE",
                record.get(NFL_TEAM_CSV_HEADERS.WebSite)
            ));
        }
    }

    void migrateCFBTeams() throws IOException {

        File file = storageService.load("cfb/teams.csv").toFile();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
            .setHeader(NCAA_TEAM_CSV_HEADERS.class)
            .setSkipHeaderRecord(true)
            .build();

        Iterable<CSVRecord> records = csvFormat.parse(new FileReader(file));
        cfbTeamService.clear();
        for (CSVRecord record : records) {
            cfbTeamService.save(new CFBTeam(
                record.get(NCAA_TEAM_CSV_HEADERS.School),
                record.get(NCAA_TEAM_CSV_HEADERS.Code),
                record.get(NCAA_TEAM_CSV_HEADERS.Nickname),
                record.get(NCAA_TEAM_CSV_HEADERS.City),
                record.get(NCAA_TEAM_CSV_HEADERS.State),
                record.get(NCAA_TEAM_CSV_HEADERS.Conference),
                Arrays.asList(record.get(NCAA_TEAM_CSV_HEADERS.Colors).split(";"))
            ));

            encService.save(new Encyclopedia(
                record.get(NCAA_TEAM_CSV_HEADERS.Code),
                "CFB",
                "TEAM_LOGO",
                record.get(NCAA_TEAM_CSV_HEADERS.LogoURL)
            ));
        }
    }

    void migrateMLBTeams() throws IOException {
        File file = storageService.load("mlb/teams.csv").toFile();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
            .setHeader(MLB_TEAM_CSV_HEADERS.class)
            .setSkipHeaderRecord(true)
            .build();

        Iterable<CSVRecord> records = csvFormat.parse(new FileReader(file));
        mlbTeamService.clear();
        for (CSVRecord record : records) {
            mlbTeamService.save(new MLBTeam(
                record.get(MLB_TEAM_CSV_HEADERS.Code),
                record.get(MLB_TEAM_CSV_HEADERS.Team),
                record.get(NFL_TEAM_CSV_HEADERS.Division),
                record.get(MLB_TEAM_CSV_HEADERS.League),
                Integer.parseInt(record.get(MLB_TEAM_CSV_HEADERS.Founded))
            ));

            encService.save(new Encyclopedia(
                record.get(NFL_TEAM_CSV_HEADERS.Code),
                "MLB",
                "TEAM_LOGO",
                record.get(MLB_TEAM_CSV_HEADERS.LogoURL)
            ));
        }
    }
    
    void migrateNFLMatches() throws IOException {
        File file = storageService.load("nfl/matches.csv").toFile();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
            .setHeader(NFL_MATCH_CSV_HEADERS.class)
            .setSkipHeaderRecord(true)
            .build();

        Iterable<CSVRecord> records = csvFormat.parse(new FileReader(file));
        nflMatchService.clear();
        for (CSVRecord record : records) {
            nflMatchService.save(new NFLMatch
            (
                record.get(NFL_MATCH_CSV_HEADERS.Team1),
                record.get(NFL_MATCH_CSV_HEADERS.Team2),
                Integer.parseInt(record.get(NFL_MATCH_CSV_HEADERS.Score1)),
                Integer.parseInt(record.get(NFL_MATCH_CSV_HEADERS.Score2)),
                Integer.parseInt(record.get(NFL_MATCH_CSV_HEADERS.Round)),
                Integer.parseInt(record.get(NFL_MATCH_CSV_HEADERS.Season))
            ));
        }
    }
    
    void migrateNFLPlayers() {
        for(NFLTeam team : nflTeamService.findAll()) {
            for(NFLPlayer player : getPlayersFromStorage(team.getCode())) {
                nflPlayerService.save(player);
            }
        }
    }

    private List<NFLPlayer> getPlayersFromStorage(String teamCode) {
        File file = storageService.load("nfl/roster/" + teamCode + ".csv").toFile();

        try {
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
            .setHeader(NFL_PLAYER_CSV_HEADERS.class)
            .setSkipHeaderRecord(true)
            .build();

            Iterable<CSVRecord> records = csvFormat.parse(new FileReader(file));
            return StreamSupport.stream(records.spliterator(), false).map( record -> 
                new NFLPlayer(
                    teamCode,
                    record.get(NFL_PLAYER_CSV_HEADERS.Name),
                    record.get(NFL_PLAYER_CSV_HEADERS.Position),
                    record.get(NFL_PLAYER_CSV_HEADERS.Number),
                    record.get(NFL_PLAYER_CSV_HEADERS.Weight),
                    record.get(NFL_PLAYER_CSV_HEADERS.Height),
                    record.get(NFL_PLAYER_CSV_HEADERS.College)
                )
            ).toList();
            
        } catch(IOException ioe) {
            return new ArrayList<>(); 
        }
    }
}
