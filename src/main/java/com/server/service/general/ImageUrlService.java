package com.server.service.general;

import java.util.List;
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

    public Map<String, String> findByTypeAndName(String type, String name) {
        return imageUrlRepository.getByTypeAndName(type, name).stream().collect(Collectors.toMap(ImageUrl::getCode, ImageUrl::getValue));
    }

    public List<ImageUrl> findByType(String type) {
        return imageUrlRepository.getByType(type);
    }

    public String find(String type, String name, String code) {
        return imageUrlRepository.getOne(type, name, code).getValue();
    }

}
