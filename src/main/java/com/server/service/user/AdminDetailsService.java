package com.server.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.server.document.user.Admin;
import com.server.exception.NoRecordException;
import com.server.repository.user.AdminRepository;
import com.server.security.AdminDetails;

@Service
public class AdminDetailsService implements UserDetailsService {
    @Autowired
    private AdminRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Admin admin = repository.getByUsername(username);

            return new AdminDetails(admin);
        } catch (NoRecordException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
    
}
