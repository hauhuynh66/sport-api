package com.server.model.user;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "user")
@Getter
@Setter
public class User {
    @Id
    private String id;

    private String email;

    private String password;

    private Set<String> authorities;

    public User(String email, String pwd) {
        this.email = email;
        this.password = pwd;
    }
}
