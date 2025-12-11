package com.healthpal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Health alert response data")
public class HealthAlertDTO {

    @Schema(description = "Alert ID")
    private Integer alertId;

    @Schema(description = "Alert title")
    private String title;

    @Schema(description = "Alert description")
    private String description;

    @Schema(description = "Affected region")
    private String region;

    @Schema(description = "Date posted", example = "2025-02-01")
    private String datePosted;
}
