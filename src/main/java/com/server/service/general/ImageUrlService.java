package com.server.service.general;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.document.general.ImageUrl;
import com.server.repository.general.ImageUrlRepository;

@Service
public class ImageUrlService {
    @Autowired
    private ImageUrlRepository imageUrlRepository;

    public Map<String, String> findByType(String type) {
        return imageUrlRepository.getByType(type).stream().collect(Collectors.toMap(ImageUrl::getCode, ImageUrl::getValue));
    }

    public String findByTypeAndCode(String type, String code) {
        return imageUrlRepository.getByTypeAndCode(type, code).getValue();
    }

}
