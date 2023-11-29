package com.server.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * File storage service
 * 
 * @author  Hauhp
 * @version v0.0.1
 */
@Service
public class StorageService implements IStorage {
    private final Path root;
    @Autowired
    public StorageService(StorageProperties properties) {
        this.root = Paths.get(properties.getRootPath());
    }

    /**
     * Initialize storage
     */
    @Override
    public void init() {
        try{
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /** 
     * Save file to storage
     * @param file
     * @return String
     */
    @Override
    public String store(MultipartFile file) {
        try{
            if(file.isEmpty()){
                throw new IOException("File is empty");
            }
            String now = String.valueOf(System.currentTimeMillis());
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());

            Path des = this.root.resolve(Paths.get(now + "." + extension)).normalize().toAbsolutePath();
            if(!des.getParent().equals(this.root.toAbsolutePath())){
                throw new IOException("Cannot store file outside " + this.root.toAbsolutePath().toString());
            }
            try (InputStream is = file.getInputStream()){
                Files.copy(is, des, StandardCopyOption.REPLACE_EXISTING);
                
            }
            return des.toString();
        }catch (IOException e){
            return null;
        }

    }

    /**
     * Load file from storage
     * @param filename
     * @return Path
     */
    @Override
    public Path load(String filename) {
        return root.resolve(filename);
    }

    /**
     * Load file from storage as resource type
     * @param filename
     * @return Resource
     */
    @Override
    public Resource loadAsResource(String filename) {
        return null;
    }

    /**
     * Recursively delete all file in storage
     */
    @Override
    public void flush() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }
}
