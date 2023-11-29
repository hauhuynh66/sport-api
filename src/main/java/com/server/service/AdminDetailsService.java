package com.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.server.model.Admin;
import com.server.repository.AdminRepositoryImpl;
import com.server.security.AdminDetails;

public class AdminDetailsService implements UserDetailsService {
    @Autowired
    private AdminRepositoryImpl adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.getByEmail(username);

        if(admin == null) {
            throw new UsernameNotFoundException("Wrong credentials");
        }

        return new AdminDetails(admin);
    }
    
}
