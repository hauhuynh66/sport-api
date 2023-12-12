package com.server.controller.api.work;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.server.storage.UploadStorageService;

@RestController
@RequestMapping("/test/v1/work")
public class WorkController {
    @Autowired
    private UploadStorageService uploadStorage;

    @PostMapping("/cxsast")
    public String cxsastAvisor(@RequestParam("file") MultipartFile file) {
        Path path = uploadStorage.store(file);

        return "OK";
    }
}
