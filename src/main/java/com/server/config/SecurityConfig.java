package com.server.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Security Config
 * @author  Hauhp
 * @version v0.0.1
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

		authenticationProvider.setPasswordEncoder(encoder());
        return new ProviderManager(authenticationProvider);
    }

    /** 
     * Http server security config
     * @param http
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.cors().configurationSource(corsConfigurationSource());

        http.authorizeHttpRequests((request) -> {
                    request.requestMatchers("/admin/**").authenticated();
                    request.anyRequest().permitAll();
        })
        .formLogin((form) -> {
            form
                .loginPage("/admin/login")
                .permitAll();
        })
        .logout((logout) -> logout.permitAll());

        // JWTAuthenticationFilter filter = new JWTAuthenticationFilter(authenticationManager());
        // filter.setFilterProcessesUrl("/api/login");
        // http.addFilter(filter);
        // http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
	public UserDetailsService userDetailsService() {
		UserDetails user =
			User.builder()
				.username("hauhp")
				.password(encoder().encode("hauhp"))
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
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