package com.server.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;

@Document(collection = "admin")
@NoArgsConstructor
@Getter
@Setter
public class Admin {
    @Id
    private String id;

    private String email;

    private String password;

    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
