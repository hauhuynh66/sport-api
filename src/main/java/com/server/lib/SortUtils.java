package com.server.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SortUtils {
    public static <K extends Comparable<?>, V> List<K> getSortedMapKey(Map<K, V> map) {
        TreeMap<K, V> sortedMap = new TreeMap<>(map);
        List<K> sortedKeys = new ArrayList<>(sortedMap.keySet());
        return sortedKeys;
    }
}
