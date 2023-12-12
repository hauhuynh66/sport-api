package com.server.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.server.storage.StorageService;

@Component
public class JapaneseDictionaryBatch implements CommandLineRunner {
    @Autowired
    private StorageService storageService;

    @Override
    public void run(String[] args) throws Exception {
        // File file = storageService.load("JMdict_e.xml").toFile();

        // DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        // Document doc = builder.parse(file);
    }
    
}
