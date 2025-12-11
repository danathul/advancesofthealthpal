// src/main/java/com/healthpal/entity/TraumaCounselingSession.java
package com.healthpal.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "trauma_counseling_sessions")
@Data
public class TraumaCounselingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private String issueDescription;

    private boolean isAnonymous = true;

    private String status = "PENDING"; // PENDING, IN_PROGRESS, COMPLETED

    private LocalDateTime requestedAt = LocalDateTime.now();

    private LocalDateTime sessionTime;

    @ManyToOne
    private User counselor;
}