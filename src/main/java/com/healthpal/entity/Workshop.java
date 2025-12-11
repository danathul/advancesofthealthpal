package com.healthpal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Entity
@Table(name = "workshops")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Workshop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workshop_id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String location;

    private LocalDate event_date;

    private String speaker;

    private Integer capacity;

    private LocalDateTime created_at = LocalDateTime.now();

    @Column(name = "created_by")
    private Integer createdBy;
}
