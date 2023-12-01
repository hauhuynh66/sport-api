package com.server.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.server.model.user.User;
import com.server.repository.user.UserRepositoryImpl;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepositoryImpl userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByEmail(username);
        System.out.println(user);

        if(user == null) {
            throw new UsernameNotFoundException("Bad credentials");
        }

        return new CustomUserDetails(user);

    }
    
}
