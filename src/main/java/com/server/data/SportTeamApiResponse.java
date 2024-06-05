package com.server.data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.server.document.general.Encyclopedia;

public class SportTeamApiResponse<T> { 
    private T object;
    
    private Map<String, String> properties;
    
    public SportTeamApiResponse(T t, List<Encyclopedia> map) {
        this.object = t;
        this.properties = map.stream().collect(
            Collectors.toMap(Encyclopedia::getName, Encyclopedia::getValue)
        );
    }
}
