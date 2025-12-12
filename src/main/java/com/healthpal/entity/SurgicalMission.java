package com.healthpal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
//rahaf's part
@Entity
@Table(name = "surgical_missions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurgicalMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer surgery_id;

    private Integer mission_id;

    private String surgery_type;

    private Integer capacity;

    private LocalDate scheduled_date;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private LocalDateTime created_at = LocalDateTime.now();
}
