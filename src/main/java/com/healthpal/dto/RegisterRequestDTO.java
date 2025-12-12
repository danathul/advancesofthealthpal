package com.healthpal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "User registration request")
public class RegisterRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Schema(description = "Full name", example = "Ahmad Hassan")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Email address", example = "ahmad@example.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Schema(description = "Password", example = "password123")
    private String password;

    @NotBlank(message = "Role is required")
    @Schema(description = "User role: PATIENT, DOCTOR, COUNSELOR", example = "PATIENT")
    private String role;

    @Schema(description = "Phone number", example = "0599123456")
    private String phone;

    @Schema(description = "Age (for patients)", example = "25")
    private Integer age;

    @Schema(description = "Gender: M, F, Other", example = "M")
    private String gender;

    @Schema(description = "Specialization (for doctors)", example = "Cardiology")
    private String specialization;

    @Schema(description = "License number (for doctors)", example = "LIC123456")
    private String licenseNo;
}