package com.healthpal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Medical mission response data")
public class MissionDTO {

    @Schema(description = "Mission ID")
    private Integer id;

    @Schema(description = "Mission title")
    private String name;

    @Schema(description = "Mission description")
    private String description;

    @Schema(description = "Region", example = "Gaza")
    private String region;

    @Schema(description = "Mission date", example = "2025-03-01")
    private String date;
}
