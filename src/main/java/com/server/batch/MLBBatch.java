package com.server.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
    private StorageService storageService;

    @Override
    public void run(String... args) throws Exception {
        if(init) {
            
        }
    }
}
