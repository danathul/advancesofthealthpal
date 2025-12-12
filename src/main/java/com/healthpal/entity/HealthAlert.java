// src/main/java/com/healthpal/entity/HealthAlert.java
package com.healthpal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
//rahaf's part
@Entity
@Table(name = "health_alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class HealthAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String content;
    private String severity; // LOW, MEDIUM, HIGH, CRITICAL
    private String region;

    private LocalDateTime publishedAt = LocalDateTime.now();
    private boolean active = true;
}