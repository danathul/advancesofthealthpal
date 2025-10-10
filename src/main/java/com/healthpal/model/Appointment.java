package com.healthpal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class Appointment {
    @Id @GeneratedValue
    private Long id;
    private LocalDateTime datetime;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    private String status; // booked, completed, cancelled
}
