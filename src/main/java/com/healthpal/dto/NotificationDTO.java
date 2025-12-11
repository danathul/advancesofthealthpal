package com.healthpal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Notification data")
public class NotificationDTO {

    @Schema(description = "Notification title", example = "New Health Alert")
    private String title;

    @Schema(description = "Message content", example = "Heatwave expected tomorrow")
    private String message;

    @Schema(description = "Type: ALERT, REMINDER, INFO", example = "ALERT")
    private String type;

    @Schema(description = "Receiver user ID", example = "2")
    private Integer userId;

    @Schema(description = "Creator user ID", example = "4")
    private Integer createdBy;
}
