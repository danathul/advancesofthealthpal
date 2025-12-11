package com.healthpal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "Donation transaction")
public class DonationDTO {
    private Integer id;
    private Integer caseId;
    private Integer donorId;
    private BigDecimal amount;
    private LocalDateTime donatedAt;
    private Boolean anonymous;
    private String messageToPatient;
    private String donorName; // لو مش anonymous
}