package com.server.migration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.server.document.user.Admin;
import com.server.repository.user.AdminRepository;

@Component
public class UserMigration implements CommandLineRunner {
    @Value("${data.user.migrate}")
    private boolean migrate = false;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void run(String... args) throws Exception {
        addAdmin();
    }

    public void addAdmin() {
        if(migrate) {
            adminRepository.clear();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
            Admin hauhp = new Admin("hauhp", encoder.encode("hauhp"));

            adminRepository.save(hauhp);
        }
    }
    
}
