// src/main/java/com/healthpal/entity/Donation.java
package com.healthpal.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
@Data
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "case_id", nullable = false)
    private TreatmentCase treatmentCase;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private User donor; // ممكن يكون anonymous

    @Column(precision = 12, scale = 2)
    private BigDecimal amount;

    private LocalDateTime donatedAt = LocalDateTime.now();

    private String transactionId; // من بوابة الدفع أو مرجع يدوي
    private Boolean anonymous = false;
    private String messageToPatient;
}