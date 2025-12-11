package com.healthpal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "Medical sponsorship case")
public class TreatmentCaseDTO {
    private Integer id;
    private Integer patientId;
    private String title;
    private String description;
    private BigDecimal requiredAmount;
    private BigDecimal collectedAmount;
    private String medicalReportUrl;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
    private Double progressPercentage; // محسوب
}