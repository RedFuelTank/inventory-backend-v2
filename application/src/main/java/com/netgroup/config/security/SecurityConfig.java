package com.netgroup.config.security;

import com.netgroup.config.security.handler.ApiAccessDeniedEntryPoint;
import com.netgroup.config.security.handler.ApiEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    public static final int ENCODE_STRENGTH = 10;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin();

        http.exceptionHandling()
                .authenticationEntryPoint(new ApiEntryPoint())
                .accessDeniedHandler(new ApiAccessDeniedEntryPoint());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetails() {
        UserDetails user = User.builder()
                .username("user")
                .password("$2a$10$72smKiEoOePeQwhidRB/8uV8ZrBrDtQZ.lLVC8S7puLVow7gpE14a")
                .authorities("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("$2a$10$kALlgpMNrQyV0hZcA5bS8e65m6OgiE0YMWU1jcX.kcKLFp0yRqHQy")
                .authorities("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(ENCODE_STRENGTH);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(ENCODE_STRENGTH);
        String userPassword = "user";
        System.out.println("User encoded password: " + encoder.encode(userPassword));

        String adminPassword = "admin";
        System.out.println("Admin encoded password: " + encoder.encode(adminPassword));
    }
}
