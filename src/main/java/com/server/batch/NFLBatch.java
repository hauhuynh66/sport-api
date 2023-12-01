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
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.server.model.nfl.NFLTeam;
import com.server.repository.nfl.NFLTeamRepositoryImpl;
import com.server.storage.StorageService;

enum HEADERS {
    ShortName,Name,Abbreviation,Conference,Division
}

@Component
public class NFLBatch implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(NFLBatch.class);

    @Autowired
    private NFLTeamRepositoryImpl nflTeamRepository;

    @Autowired
    private StorageService storageService;

    @Override
    public void run(String... args) throws Exception {
        // logger.info("CLEAR NFL");
        // nflTeamRepository.clear();

        // logger.info("ADD NFL");
        // for (NFLTeam team : getTeamList()) {
        //     nflTeamRepository.save(team);
        // }
    }

    Collection<NFLTeam> getTeamList() throws IOException {
        ArrayList<NFLTeam> teams = new ArrayList<>();

        File file = storageService.load("nfl_teams.csv").toFile();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
            .setHeader(HEADERS.class)
            .setSkipHeaderRecord(true)
            .build();

        Iterable<CSVRecord> records = csvFormat.parse(new FileReader(file));

        for (CSVRecord record : records) {
            teams.add(new NFLTeam(
                record.get(HEADERS.ShortName),
                record.get(HEADERS.Name),
                record.get(HEADERS.Abbreviation),
                record.get(HEADERS.Division),
                record.get(HEADERS.Conference),
                ""
            ));
        }

        return teams;
    }
    
}
