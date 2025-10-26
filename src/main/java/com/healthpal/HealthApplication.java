package com.healthpal;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "HealthPal API - Group 1",
                version = "1.0",
                description = "Remote Consultations & Mental Health Support"
        )
)
public class HealthApplication {
    public static void main(String[] args) {
        SpringApplication.run(HealthApplication.class, args);
        System.out.println("Swagger: http://localhost:8081/swagger-ui.html");
    }
}