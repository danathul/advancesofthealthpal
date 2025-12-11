package com.healthpal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Schema(description = "Create Workshop request")
public class WorkshopCreateDTO {

    @Schema(description = "Workshop title", example = "First Aid Basics")
    private String title;

    @Schema(description = "Category", example = "Emergency")
    private String category;

    @Schema(description = "Language", example = "Arabic")
    private String language;

    @Schema(description = "Region", example = "Nablus")
    private String region;

    @Schema(description = "Image URL")
    private String imageUrl;

    @Schema(description = "Short description")
    private String description;

    @Schema(description = "Workshop date", example = "2025-03-05")
    private LocalDate date;

    @Schema(description = "Workshop time", example = "15:30")
    private LocalTime time;

    @Schema(description = "Creator user ID", example = "4")
    private Integer createdBy;
}
