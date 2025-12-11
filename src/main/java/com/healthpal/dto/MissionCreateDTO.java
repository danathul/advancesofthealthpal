package com.healthpal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Create mission request")
public class MissionCreateDTO {

    @Schema(description = "Mission title", example = "Blood Pressure Awareness Week")
    private String title;

    @Schema(description = "Category", example = "Public Health")
    private String category;

    @Schema(description = "Language", example = "Arabic")
    private String language;

    @Schema(description = "Region", example = "Gaza")
    private String region;

    @Schema(description = "Image URL")
    private String imageUrl;

    @Schema(description = "Description")
    private String description;

    @Schema(description = "Difficulty level", example = "MEDIUM")
    private String difficultyLevel;

    @Schema(description = "Mission points", example = "50")
    private Integer points;

    @Schema(description = "Creator user ID", example = "4")
    private Integer createdBy;
}
