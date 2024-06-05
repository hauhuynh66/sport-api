package com.server.lib.util;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.server.document.general.Encyclopedia;

public class EncyclopediaUtils {

    public static Map<String, String> encMap(Iterable<Encyclopedia> encs) {
        return StreamSupport.stream(encs.spliterator(), false).collect(Collectors.toMap(Encyclopedia::getName, Encyclopedia::getValue));
    }
}