package com.healthpal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Request for medicine delivery")
public class MedicineRequestDTO {
    private Integer id;
    private Integer patientId;
    private String medicineName;
    private Integer quantity;
    private String dosage;
    private String location;
    private String phone;
    private String status;
    private LocalDateTime createdAt;
    private Integer fulfilledById;
}