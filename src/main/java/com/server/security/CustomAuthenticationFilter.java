package com.server.security;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * JWT token authentication filter
 * 
 * @author  Hauhp
 * @version v0.0.1
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private final byte[] secret = "secret".getBytes();
    private final Algorithm algorithm = Algorithm.HMAC256(secret);

    private static final long interval = 60*60*1000;
    
    /** 
     * Process login request
     * @param request
     * @param response
     * @return Authentication
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            LoginRequest loginRequest = mapper.readValue(request.getReader(), LoginRequest.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword());
            System.out.println(authenticationManager.authenticate(token));
            return authenticationManager.authenticate(token);
        }catch (Exception e){
            throw new AuthenticationCredentialsNotFoundException(e.getMessage());
        }
    }

    /**
     * Handle successful authentication
     * @param request
     * @param response
     * @param chain
     * @param authResult
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws ServletException, IOException {
        UserDetails user = (UserDetails) authResult.getPrincipal();
        Date expiredTime = new Date(System.currentTimeMillis()+interval);
        Date refreshExpiredTime = new Date(System.currentTimeMillis()+2*interval);

        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiredTime)
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(user.getUsername()+"@refresh")
                .withExpiresAt(refreshExpiredTime)
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        response.setHeader("Access-Control-Expose-Headers", "x-token, username");
        response.setHeader("username", user.getUsername());
        response.setHeader("x-token", accessToken);

        Cookie cookie = new Cookie("refresh_token", refreshToken);

        response.addCookie(cookie);
    }

    /**
     * Handle unsuccessful authentication
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }

    /**
     * Login request data object
     * 
     * @author  Hauhp
     * @version v0.0.1
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    private static class LoginRequest{
        private String username;
        private String password;
    }
}
