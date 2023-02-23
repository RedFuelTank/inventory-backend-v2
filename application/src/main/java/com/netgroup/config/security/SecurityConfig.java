package com.netgroup.config.security;

import com.netgroup.config.security.handler.ApiAccessDeniedEntryPoint;
import com.netgroup.config.security.handler.ApiEntryPoint;
import com.netgroup.config.security.jwt.JwtAuthenticationFilter;
import com.netgroup.config.security.jwt.JwtAuthorizationFilter;
import com.netgroup.config.security.jwt.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    public static final int ENCODE_STRENGTH = 10;
    private final JwtConfig jwtConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.exceptionHandling()
                .authenticationEntryPoint(new ApiEntryPoint())
                .accessDeniedHandler(new ApiAccessDeniedEntryPoint());

        http.apply(new FilterConfigurer());

        http.authorizeHttpRequests()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/**").authenticated();

        return http.build();
    }

    class FilterConfigurer extends AbstractHttpConfigurer<FilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager manager = http.getSharedObject(AuthenticationManager.class);
            http.addFilterBefore(
                    new JwtAuthenticationFilter(manager, "/login", jwtConfig),
                    UsernamePasswordAuthenticationFilter.class
            );
            http.addFilterBefore(
                    new JwtAuthorizationFilter(jwtConfig.getKey()),
                    AuthorizationFilter.class);

            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
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
}

