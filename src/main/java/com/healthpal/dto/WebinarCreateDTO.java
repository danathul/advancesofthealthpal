package com.healthpal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
//rahaf's part
@Data
@Schema(description = "Create Webinar request")
public class WebinarCreateDTO {

    @Schema(description = "Webinar title", example = "Managing Diabetes")
    private String title;

    @Schema(description = "Category", example = "Chronic Disease")
    private String category;

    @Schema(description = "Language", example = "Arabic")
    private String language;

    @Schema(description = "Region", example = "Hebron")
    private String region;

    @Schema(description = "Image URL")
    private String imageUrl;

    @Schema(description = "Speaker name", example = "Dr. Ali Omar")
    private String speaker;

    @Schema(description = "Webinar link", example = "https://zoom.com/abc123")
    private String link;

    @Schema(description = "Date", example = "2025-04-01")
    private LocalDate date;

    @Schema(description = "Time", example = "18:00")
    private LocalTime time;

    @Schema(description = "Creator user ID", example = "4")
    private Integer createdBy;
}
