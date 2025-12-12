package com.healthpal.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String phone;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // 👇 أضف DONOR هنا
    public enum Role {
        PATIENT,
        DOCTOR,
        COUNSELOR,
        DONOR,
        ADMIN,
        NGO        // 👈 أضفنا هذه، وهي سبب المشكلة الحالية
    }
}
