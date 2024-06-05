package com.server.service.general;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.document.general.Encyclopedia;
import com.server.repository.general.EncyclopediaRepository;

@Service
public class EncyclopediaService {
    @Autowired
    private EncyclopediaRepository encRepository;

    public String save(Encyclopedia enc) {
        return encRepository.save(enc);
    }

    public List<Encyclopedia> findByType(String type) {
        return encRepository.getByType(type.toUpperCase());
    }

    public List<Encyclopedia> findByTypeAndName(String type, String name) {
        return encRepository.getByTypeAndName(type.toUpperCase(), name.toUpperCase());
    }

    public List<Encyclopedia> findByTypeAndCode(String type, String code) {
        return encRepository
                .getByTypeAndCode(type.toUpperCase(), code.toUpperCase());
    }

    public Encyclopedia find(String type, String name, String code) {
        return encRepository.getOne(type, name, code);
    }
}
