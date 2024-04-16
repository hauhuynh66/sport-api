package com.server.document.general;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "image_url")
@NoArgsConstructor
@Getter
@Setter
public class ImageUrl {
    @Id
    @JsonIgnore
    private String id;

    @Indexed
    private String code;
    
    @JsonIgnore
    private String type;

    private String value;

    public ImageUrl(String code, String type, String value) {
        this.code = code;
        this.type = type;
        this.value = value;
    }
}