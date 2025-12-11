package com.healthpal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Login request")
public class LoginRequestDTO {

    @Schema(description = "Email address", example = "ahmad@example.com")
    private String email;

    @Schema(description = "Password", example = "password123")
    private String password;
}