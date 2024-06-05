package com.server.document.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Document(collection = "admin")
@Data
public class Admin {
    @Id
    @JsonIgnore
    private String id;

    @NotNull
    @Indexed
    private String username;

    @NotNull
    @JsonIgnore
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
