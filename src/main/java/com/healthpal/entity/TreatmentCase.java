package com.healthpal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode; // ضيفي هذا

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
    private String status = "OPEN";
    private String supportedByNgo;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime deadline;

    // 👇👇 التعديل الحاسم: أضفنا Exclude للـ Equals والـ HashCode والـ ToString
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "treatmentCase", cascade = CascadeType.ALL)
    private List<Donation> donations = new ArrayList<>();
}