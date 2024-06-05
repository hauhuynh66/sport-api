package com.server.repository.general;

import java.util.List;

import com.server.document.general.Encyclopedia;

public interface EncyclopediaRepository {
    String save(Encyclopedia enc);
    List<Encyclopedia> getByType(String type);
    List<Encyclopedia> getByTypeAndName(String type, String name);
    List<Encyclopedia> getByTypeAndCode(String type, String name);
    Encyclopedia getOne(String type, String name, String code);
    void clear();
}