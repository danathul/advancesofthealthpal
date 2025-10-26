package com.healthpal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Support group data")
public class SupportGroupDTO {
    private Integer id;

    @Schema(description = "Group name", example = "Anxiety Support")
    private String name;

    @Schema(description = "Group description", example = "Talk about anxiety and coping")
    private String description;

    @Schema(description = "Creator user ID", example = "1")
    private Integer createdBy;
}