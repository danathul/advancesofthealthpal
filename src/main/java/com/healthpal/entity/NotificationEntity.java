package com.healthpal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
//rahaf's part
@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notification_id;

    private Integer user_id;

    @Column(columnDefinition = "ENUM('share','comment','report','webinar','workshop','mission')")
    private String type;

    private String reference_table;

    private Integer reference_id;

    @Column(columnDefinition = "TEXT")
    private String message;

    private Boolean is_read = false;

    private LocalDateTime created_at = LocalDateTime.now();
}
