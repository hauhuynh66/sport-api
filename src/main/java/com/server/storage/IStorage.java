package com.server.storage;

import java.nio.file.Path;

import org.springframework.core.io.Resource;

/**
 * Storage interface
 * 
 * @author  Hauhp
 * @version v0.0.1
 */
public interface IStorage {
    void init();
    Path load(String filename);
    Resource loadAsResource(String filename);
    default void flush() {
        
    }
}
