package com.healthpal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "health_guides")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthGuide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer guide_id;

    @Column(nullable = false)
    private String title;

    private String category;

    @Column(length = 10)
    private String language = "ar";

    private String region;

    private String image_url;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private LocalDateTime created_at = LocalDateTime.now();

    // creator user reference (nullable)
    @Column(name = "created_by")
    private Integer createdBy;
}
