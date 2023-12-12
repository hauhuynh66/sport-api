package com.server.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.server.storage.StorageService;
import com.server.storage.UploadStorageService;

@Component
public class InitializeRunner implements CommandLineRunner {
    
	@Autowired
	private UploadStorageService uploadService;

    @Autowired
	private StorageService storageService;

    @Override
    public void run(String... args) throws Exception {
        uploadService.init();
        storageService.init();
    }
    

}
