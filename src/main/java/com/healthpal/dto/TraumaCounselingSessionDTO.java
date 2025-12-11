// src/main/java/com/healthpal/dto/TraumaCounselingSessionDTO.java
package com.healthpal.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TraumaCounselingSessionDTO {
    private Integer id;
    private Integer patientId;
    private String issueDescription;
    private boolean isAnonymous = true;
    private String status;
    private LocalDateTime requestedAt;
    private Integer counselorId;
}