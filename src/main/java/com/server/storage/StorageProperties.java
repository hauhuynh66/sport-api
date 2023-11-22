package com.server.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Add storage to application.properties
 * 
 * @author  Hauhp
 * @version v0.0.1
 */
@Configuration
@ConfigurationProperties("storage")
@Data
public class StorageProperties {
    private String path = "storage";
}
