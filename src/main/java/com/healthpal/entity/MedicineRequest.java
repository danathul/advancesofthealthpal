package com.healthpal.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "medicine_requests")
@Data
public class MedicineRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private String medicineName;
    private Integer quantity;
    private String dosage;
    private String location;
    private String phone;

    // ← غيرناه من Enum إلى String
    private String status = "PENDING"; // PENDING, FULFILLED, CANCELLED

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime fulfilledAt;

    @ManyToOne
    @JoinColumn(name = "fulfilled_by")
    private User fulfilledBy;

    // لو عايز تحط fulfilledById بدل الكائن كله، استخدم ده:
    // private Integer fulfilledById;
}