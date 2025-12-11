package com.healthpal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Health guide response data")
public class HealthGuideDTO {

    @Schema(description = "Guide ID")
    private Integer guideId;

    @Schema(description = "Guide title")
    private String title;

    @Schema(description = "Guide content")
    private String content;

    @Schema(description = "Category")
    private String category;

    @Schema(description = "Creation date", example = "2025-01-15")
    private String createdAt;
}
