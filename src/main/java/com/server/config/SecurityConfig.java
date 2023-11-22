package com.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.server.security.CustomAuthenticationFilter;
import com.server.security.CustomAuthorizationFilter;

/**
 * Spring Security Config
 * @author  Hauhp
 * @version v0.0.1
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    private AuthenticationConfiguration configuration;

    /** 
     * Authentication manager injection
     * @return AuthenticationManager
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return configuration.getAuthenticationManager();
    }

    /** 
     * Http server security config
     * @param http
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((request) -> {
            request
                .requestMatchers("/api/**", "/login").permitAll()
                .anyRequest().authenticated();
        });
        
        // Authentication and authorization filter config
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter(authenticationManager());
        http.addFilter(filter);
        http.addFilterBefore(new CustomAuthorizationFilter(), CustomAuthenticationFilter.class);

        return http.build();
    }
}