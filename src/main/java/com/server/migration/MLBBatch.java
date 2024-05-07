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
import com.server.document.mlb.MLBTeam;
import com.server.repository.general.ImageUrlRepository;
import com.server.repository.mlb.MLBTeamRepository;
import com.server.storage.StorageService;

enum MLB_TEAM_CSV_HEADERS {
    League, Division, Team, City, Founded, Code
}

@Component
public class MLBBatch implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(MLBBatch.class);

    @Value("${data.mlb.init}")
    private boolean init = false;

    @Autowired
    private MLBTeamRepository mlbTeamRepository;

    @Autowired
    private ImageUrlRepository imageUrlRepository;

    @Autowired
    private StorageService storageService;

    @Override
    public void run(String... args) throws Exception {
        if(init) {
            for (MLBTeam team : getTeamList()) {
                mlbTeamRepository.save(team);
                imageUrlRepository.save(
                    new ImageUrl(
                        team.getCode(),
                        "MLB",
                        "TEAM_LOGO",
                        ""
                    )
                );
            }
        }
    }

    Collection<MLBTeam> getTeamList() throws IOException {
        ArrayList<MLBTeam> teams = new ArrayList<>();

        File file = storageService.load("mlb/teams.csv").toFile();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
            .setHeader(MLB_TEAM_CSV_HEADERS.class)
            .setSkipHeaderRecord(true)
            .build();

        Iterable<CSVRecord> records = csvFormat.parse(new FileReader(file));

        for (CSVRecord record : records) {
            teams.add(new MLBTeam(
                record.get(MLB_TEAM_CSV_HEADERS.Code),
                record.get(MLB_TEAM_CSV_HEADERS.Team),
                record.get(NFL_TEAM_CSV_HEADERS.Division),
                record.get(MLB_TEAM_CSV_HEADERS.League),
                Integer.parseInt(record.get(MLB_TEAM_CSV_HEADERS.Founded))
            ));
        }

        return teams;
    }
}
