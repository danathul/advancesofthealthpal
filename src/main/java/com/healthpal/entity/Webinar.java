package com.healthpal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalDateTime;
//rahaf's part
@Entity
@Table(name = "webinars")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Webinar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer webinar_id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String link;

    private LocalDateTime scheduled_at;

    private String host;

    private Integer capacity;

    private LocalDateTime created_at = LocalDateTime.now();

    @Column(name = "created_by")
    private Integer createdBy;
}
