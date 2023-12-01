package com.server.model.user;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private String id;

    private String email;

    private String password;

    private Set<String> authorities;

    public User(String email, String password) {
        this.email = email;
        this.password = password;

        this.authorities = new HashSet<>();

        authorities.add("user");
    }

    public User(String email, String password, Set<String> roles) {
        this.email = email;
        this.password = password;

        this.authorities = roles;
    }
}
