package com.healthpal.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @Enumerated(EnumType.STRING)
    private Mode mode = Mode.VIDEO;

    @Column(name = "is_anonymous")
    private Boolean anonymous = false;

    @Enumerated(EnumType.STRING)
    private Type type = Type.PHYSICAL;

    private String notes;

    public enum Status { PENDING, APPROVED, CANCELLED, COMPLETED }
    public enum Mode { VIDEO, AUDIO, CHAT }
    public enum Type { PHYSICAL, MENTAL }
}