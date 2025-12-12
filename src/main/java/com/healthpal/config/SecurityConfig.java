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
                        // 1. السماح للوثائق (Swagger)
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // 2. 👇 هذا هو السطر الناقص والمهم جداً! السماح بالتسجيل والدخول
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // 3. السماح للـ APIs الخارجية (للديمو)
                        .requestMatchers("/api/v1/external/**").permitAll()
                        .requestMatchers("/api/v1/consultations/translate").permitAll()

                        // 4. أي شيء آخر يحتاج تسجيل دخول
                        // .requestMatchers("/api/v1/**").authenticated() // 👈 يمكنك تفعيل هذا لو عندك JWT
                        // 💡 نصيحة للديمو الليلة فقط: حولها لـ permitAll عشان ما تتغلب بالـ Headers في الفيديو
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.disable()); // تعطيل CSRF مهم جداً للـ Postman/Swagger
        return http.build();
    }
}