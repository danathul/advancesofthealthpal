package com.healthpal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Available medical equipment")
public class EquipmentDTO {
    private Integer id;
    private String name;
    private String type;
    private String description;
    private Integer quantityAvailable;
    private String location;
    private String contactPhone;
    private Integer providerId;
    private String providerName;
    private boolean lendable;
    private LocalDateTime listedAt;
}