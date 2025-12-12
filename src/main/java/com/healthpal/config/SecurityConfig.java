package com.healthpal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Fix CORS (Crucial for Swagger to work)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 2. Disable CSRF (Crucial for POST requests)
                .csrf(csrf -> csrf.disable())

                // 3. Authorization Rules
                .authorizeHttpRequests(auth -> auth
                        // Allow Swagger UI access
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()

                        // === EMERGENCY FIX FOR DEMO ===
                        // This line allows access to ALL endpoints (Alerts, Auth, Appointments, etc.)
                        // No more 403 Forbidden errors.
                        .requestMatchers("/api/v1/**").permitAll()

                        // Allow any other request just in case
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    /**
     * CORS Configuration Bean
     * This fixes "Network Error" or "Failed to fetch" in Swagger/Browsers
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow all origins (localhost, etc.)
        configuration.setAllowedOriginPatterns(List.of("*"));

        // Allow all HTTP methods (GET, POST, PUT, DELETE)
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allow all headers
        configuration.setAllowedHeaders(List.of("*"));

        // Allow credentials
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}