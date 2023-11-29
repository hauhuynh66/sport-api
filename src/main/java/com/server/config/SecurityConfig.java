package com.server.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
                .requestMatchers("/api/**/v1/**/**", "/nfl/logo/**").permitAll()
                .anyRequest().authenticated();
        });
        
        http.cors().configurationSource(corsConfigurationSource());

        // Authentication and authorization filter config
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter(authenticationManager());
        http.addFilter(filter);
        http.addFilterBefore(new CustomAuthorizationFilter(), CustomAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8004"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder(5);
    }
}