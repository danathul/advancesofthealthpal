package com.healthpal.dto;

import lombok.Data;

@Data
public class ConsultationDTO {

    private Long patientId;
    private Long doctorId;
    private String symptoms;
    private String notes;
}
