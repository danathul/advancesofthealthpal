package com.healthpal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/v1/auth/register", "/api/v1/auth/login").permitAll()
                        .requestMatchers("/api/v1/external/**").permitAll()  // Weather API
                        .requestMatchers("/api/v1/alerts").permitAll()  // Public health alerts

                        // Require authentication for all other API endpoints
                        .requestMatchers("/api/v1/**").authenticated()

                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.disable())  // Disable CSRF for API (use JWT in production)
                .httpBasic(basic -> {}); // Enable basic auth for testing

        return http.build();
    }
}