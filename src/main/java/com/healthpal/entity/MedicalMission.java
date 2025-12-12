package com.healthpal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
//rahaf's part
@Entity
@Table(name = "medical_missions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mission_id;

    // reference to ngos.ngo_id
    private Integer ngo_id;

    @Column(nullable = false)
    private String title;

    private String specialty;

    private String location;

    private LocalDate start_date;

    private LocalDate end_date;

    @Column(columnDefinition = "TEXT")
    private String details;

    private Boolean is_active = true;

    private LocalDateTime created_at = LocalDateTime.now();
}
