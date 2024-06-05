package com.server.data;

import java.util.Collection;

import lombok.Data;

@Data
public class ListReponse {
    private String message;
    private Integer count;
    private Collection<Object> object;
}
