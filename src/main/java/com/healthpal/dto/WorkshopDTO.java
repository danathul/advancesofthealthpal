package com.healthpal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
//rahaf's part
@Data
@Schema(description = "Workshop response data")
public class WorkshopDTO {

    @Schema(description = "Workshop ID")
    private Integer id;

    @Schema(description = "Workshop title")
    private String title;

    @Schema(description = "Description")
    private String description;

    @Schema(description = "Location", example = "Ramallah")
    private String location;

    @Schema(description = "Date", example = "2025-03-05")
    private String date;
}
