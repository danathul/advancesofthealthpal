package com.healthpal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "Appointment booking data")
public class AppointmentDTO {
    private Integer id;

    @Schema(description = "Patient ID", example = "1")
    private Integer patientId;

    @Schema(description = "Doctor ID", example = "1")
    private Integer doctorId;

    @Schema(description = "Date and time", example = "2025-10-27T10:00:00")
    private LocalDateTime dateTime;

    @Schema(description = "Status", example = "PENDING")
    private String status;

    @Schema(description = "Mode: VIDEO, AUDIO, CHAT", example = "VIDEO")
    private String mode;

    @Schema(description = "Anonymous therapy", example = "true")
    private Boolean anonymous;

    @Schema(description = "Type: PHYSICAL, MENTAL", example = "MENTAL")
    private String type;

    private String notes;
}