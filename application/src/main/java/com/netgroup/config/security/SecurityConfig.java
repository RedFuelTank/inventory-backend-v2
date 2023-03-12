package com.netgroup.config.security;

import com.netgroup.config.security.handler.ApiAccessDeniedEntryPoint;
import com.netgroup.config.security.handler.ApiEntryPoint;
import com.netgroup.config.security.jwt.JwtAuthenticationFilter;
import com.netgroup.config.security.jwt.JwtAuthorizationFilter;
import com.netgroup.config.security.jwt.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Order(0)
public class SecurityConfig {

    public static final int ENCODE_STRENGTH = 10;
    private final JwtConfig jwtConfig;

    @Configuration
    @Order(1)
    public class RepresentativeProvider {
        @Bean
        public SecurityFilterChain filterChainRepresentative(HttpSecurity http, DataSource dataSource) throws Exception {
            http.csrf().disable();

            http.securityMatcher("/user/**")
                    .authorizeHttpRequests()
                    .requestMatchers("/user/register").permitAll()
                    .requestMatchers("/user/**").hasAuthority("REPRESENTATIVE");

            AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);
            configAuthentication(sharedObject, dataSource);

            http.apply(new FilterConfigurer("/user/login"));

            return http.build();
        }

        public void configAuthentication(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception {
            auth.jdbcAuthentication().dataSource(dataSource)
                    .usersByUsernameQuery("select username, password, enabled from users where username=?")
                    .authoritiesByUsernameQuery("select username, authority from authorities where username=?");
        }
    }

    @Configuration
    @Order(2)
    public class BusinessProvider {
        @Bean
        public SecurityFilterChain filterChainBusiness(HttpSecurity http, DataSource dataSource) throws Exception {
            http.csrf().disable();

            http.securityMatcher("/business/**")
                    .authorizeHttpRequests()
                    .requestMatchers("/business/register").permitAll()
                    .requestMatchers("/business/**").hasAuthority("BUSINESS");

            AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);
            configAuthentication(sharedObject, dataSource);

            http.apply(new FilterConfigurer("/business/login"));

            return http.build();
        }

        public void configAuthentication(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception {
            auth.jdbcAuthentication().dataSource(dataSource)
                    .usersByUsernameQuery("select name, password, enabled from businesses where name=?")
                    .authoritiesByUsernameQuery("select name, authority from business_authorities where name=?");
        }
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.exceptionHandling()
                .authenticationEntryPoint(new ApiEntryPoint())
                .accessDeniedHandler(new ApiAccessDeniedEntryPoint());

        return http.build();
    }

    class FilterConfigurer extends AbstractHttpConfigurer<FilterConfigurer, HttpSecurity> {
        private String url;

        public FilterConfigurer(String url) {
            this.url = url;
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager manager = http.getSharedObject(AuthenticationManager.class);
            http.addFilterBefore(
                    new JwtAuthenticationFilter(manager, url, jwtConfig),
                    UsernamePasswordAuthenticationFilter.class
            );
            http.addFilterBefore(
                    new JwtAuthorizationFilter(jwtConfig.getKey()),
                    AuthorizationFilter.class);

            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(ENCODE_STRENGTH);
    }
}

