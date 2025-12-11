// src/main/java/com/healthpal/entity/HealthAlert.java
package com.healthpal.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "health_alerts")
@Data
public class HealthAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String content;
    private String severity; // LOW, MEDIUM, HIGH, CRITICAL
    private String region;

    private LocalDateTime publishedAt = LocalDateTime.now();
    private boolean isActive = true;
}