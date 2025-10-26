package com.healthpal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "Chat message")
public class MessageDTO {
    private Integer id;

    @Schema(description = "Appointment ID", example = "1")
    private Integer appointmentId;

    @Schema(description = "Sender user ID", example = "1")
    private Integer senderId;

    @Schema(description = "Message in original language", example = "كيف حالك؟")
    private String content;

    @Schema(description = "Translated message", example = "How are you?")
    private String translatedContent;

    private LocalDateTime timestamp;
}