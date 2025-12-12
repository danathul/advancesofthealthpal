package com.healthpal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode; // ضيفي هذا

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
@Data
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 👇👇 التعديل الحاسم هنا أيضاً
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "case_id", nullable = false)
    private TreatmentCase treatmentCase;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private User donor;

    @Column(precision = 12, scale = 2)
    private BigDecimal amount;
    private LocalDateTime donatedAt = LocalDateTime.now();
    private String transactionId;
    private Boolean anonymous = false;
    private String messageToPatient;
}