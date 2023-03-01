package com.netgroup.config.security.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.netgroup.config.security.LoginCredentials;
import com.netgroup.config.security.TokenInfo;
import com.netgroup.config.security.handler.AuthFailureHandler;
import com.netgroup.config.security.handler.AuthSuccessHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private JwtConfig jwtConfig;

    public JwtAuthenticationFilter(AuthenticationManager manager, String url, JwtConfig jwtConfig) {
        super(url);
        setAuthenticationManager(manager);

        this.jwtConfig = jwtConfig;

        setAuthenticationSuccessHandler(new AuthSuccessHandler());
        setAuthenticationFailureHandler(new AuthFailureHandler());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String body = request.getReader().lines().collect(Collectors.joining(""));

        LoginCredentials loginCredentials = new ObjectMapper().readValue(body, LoginCredentials.class);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginCredentials.getName(), loginCredentials.getPassword());

        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();

        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        String token = new JwtHelper(jwtConfig.getKey()).encode(new TokenInfo(
                user.getUsername(),
                roles
        ), getExpiration());
        response.addHeader("Authorization", "Bearer " + token);
    }

    private LocalDateTime getExpiration() {
        return LocalDateTime.now().plusMinutes(jwtConfig.getDurationMin());
    }
}
