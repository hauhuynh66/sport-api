package com.server.controller.api.work;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.server.service.misc.WorkService;
import com.server.storage.UploadStorageService;

@RestController
@RequestMapping("/test/v1/work")
public class WorkController {
    @Autowired
    private UploadStorageService uploadStorage;

    @Autowired
    private WorkService workService;

    @PostMapping("/cxsast")
    public String cxsastAvisor(
        @RequestParam("file") MultipartFile file,
        @RequestParam("rs") MultipartFile resourceList
    ) throws IOException {
        Path path = uploadStorage.store(file);
        Path rsPath = uploadStorage.store(resourceList);
        
        workService.fillCxSAST(path, rsPath);
        

        return "OK";
    }
}
