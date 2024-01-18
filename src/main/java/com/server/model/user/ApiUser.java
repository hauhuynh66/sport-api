package com.server.model.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "api_user")
@NoArgsConstructor
@Getter
@Setter
public class ApiUser {
    @Id
    private String id;
    private String apiKey;
}
