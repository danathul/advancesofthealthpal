package com.healthpal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
//rahaf's part
@Entity
@Table(name = "mission_volunteers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MissionVolunteer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer mission_id;

    private Integer doctor_id;

    private LocalDate available_date;

    private String role;

    private LocalDateTime created_at = LocalDateTime.now();
}
