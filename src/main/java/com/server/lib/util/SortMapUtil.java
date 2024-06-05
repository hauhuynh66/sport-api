package com.server.lib.util;

import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class SortMapUtil {
    public static Sort mapToSort(Map<String, Boolean> sortMap) {
        Sort sort = Sort.unsorted();

        if(sortMap == null) {
            return sort;
        }
        
        for (String field : sortMap.keySet()) {
            Sort.Direction direction = sortMap.get(field) ? Direction.ASC : Direction.DESC;
            sort = sort.and(Sort.by(direction,field));
        }
        return sort;
    }
}
