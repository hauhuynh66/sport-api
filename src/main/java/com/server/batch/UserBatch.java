package com.server.batch;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.server.model.user.User;
import com.server.repository.user.UserRepositoryImpl;

@Component
public class UserBatch implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(UserBatch.class);

    @Value("${data.user.init}")
    private boolean init = false;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;
    
    @Override
    public void run(String... args) throws Exception {
        if(init) {
            userRepository.clear();

            User admin = new User("hauhuynh66@gmail.com", encoder.encode("Hauhuynh"));
            userRepository.save(admin);
        }
    }
    
}
