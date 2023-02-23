package com.netgroup.config.security.jwt;

import com.netgroup.config.security.TokenInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private String jwtKey;

    public JwtAuthorizationFilter(String jwtKey) {
        this.jwtKey = jwtKey;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        TokenInfo tokenInfo = new JwtHelper(jwtKey).decode(token);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                tokenInfo.getUsername(),
                null,
                tokenInfo.getRoles().stream().map(SimpleGrantedAuthority::new).toList()
        );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
