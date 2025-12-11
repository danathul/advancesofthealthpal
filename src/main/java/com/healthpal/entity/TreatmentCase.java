package com.healthpal.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "treatment_cases")
@Data
public class TreatmentCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    private String title;
    private String description;

    @Column(precision = 12, scale = 2)
    private BigDecimal requiredAmount;

    @Column(precision = 12, scale = 2)
    private BigDecimal collectedAmount = BigDecimal.ZERO;

    private String medicalReportUrl;

    // ← غيرناه من Enum إلى String
    private String status = "OPEN"; // OPEN, FULLY_FUNDED, CLOSED, CANCELLED
    private String supportedByNgo; // اسم المنظمة الداعمة (اختياري)
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime deadline;

    @OneToMany(mappedBy = "treatmentCase", cascade = CascadeType.ALL)
    private List<Donation> donations = new ArrayList<>();
}