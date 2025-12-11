package com.healthpal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Volunteer registration data")
public class VolunteerDTO {

    @Schema(description = "Full name", example = "Mohammad Saleh")
    private String fullName;

    @Schema(description = "Email", example = "mohammad@gmail.com")
    private String email;

    @Schema(description = "Phone number", example = "0599123456")
    private String phone;

    @Schema(description = "Region", example = "Ramallah")
    private String region;

    @Schema(description = "Availability", example = "Weekends")
    private String availability;

    @Schema(description = "Skills", example = "Surgery, First Aid")
    private String skills;

    @Schema(description = "Creator user ID")
    private Integer createdBy;
}

