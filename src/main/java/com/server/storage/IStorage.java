package com.server.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

/**
 * Storage interface
 * 
 * @author  Hauhp
 * @version v0.0.1
 */
public interface IStorage {
    void init();
    default String store(MultipartFile file){
        return "";
    }
    Path load(String filename);
    Resource loadAsResource(String filename);
    default void flush() {
    }
}
