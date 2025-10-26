package com.healthpal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()  // ADD THIS LINE
                        .requestMatchers("/api/v1/**").authenticated()  // Secure your APIs
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.disable());  // Dev only
        return http.build();
    }
}