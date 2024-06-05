package com.server.document.general;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Document(collection = "encyclopedia")
@Data
public class Encyclopedia {
    @Id
    private String id;

    @Indexed
    @NotNull
    private String code;
    
    @Indexed
    @NotNull
    private String type;

    @Indexed
    @NotNull
    private String name;

    private String value;

    public Encyclopedia(String code, String type, String name, String value) {
        this.code = code;
        this.type = type;
        this.name = name;
        this.value = value;
    }
}