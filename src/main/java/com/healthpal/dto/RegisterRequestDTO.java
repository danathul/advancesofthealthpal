package com.healthpal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "User registration request")
public class RegisterRequestDTO {

    @Schema(description = "Full name", example = "Ahmad Hassan")
    private String name;

    @Schema(description = "Email address", example = "ahmad@example.com")
    private String email;

    @Schema(description = "Password", example = "password123")
    private String password;

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