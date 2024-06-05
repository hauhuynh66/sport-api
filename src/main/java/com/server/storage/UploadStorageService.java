package com.server.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadStorageService implements IStorage {
    private final Path root;

    @Autowired
    public UploadStorageService(StorageProperties properties) {
        this.root = Paths.get(properties.getUploadPath());
    }

    /**
     * Initialize storage
     */
    @Override
    public void init() {
        try{
            if(!root.toFile().exists()) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /** 
     * Save file to storage
     * @param file
     * @return String
     */
    public Path store(MultipartFile file) {
        try{
            if(file.isEmpty()){
                throw new IOException("File is empty");
            }
            Date now = new Date();
            String today = new SimpleDateFormat("yyyyMMdd").format(now);
            String time = new SimpleDateFormat("HHmmss").format(now);
            
            File directory = this.root.resolve(today).toFile();

            if(!directory.exists() || !directory.isDirectory()) {
                directory.mkdirs();
            }
            
            Path savePath = directory.toPath().resolve(time + "_" + file.getOriginalFilename());

            savePath = savePath.normalize().toAbsolutePath();
            
            try (InputStream is = file.getInputStream()){
                Files.copy(is, savePath, StandardCopyOption.REPLACE_EXISTING);
            }

            return savePath;
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
