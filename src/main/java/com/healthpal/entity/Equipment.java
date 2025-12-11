// src/main/java/com/healthpal/entity/Equipment.java
package com.healthpal.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "equipment_inventory")
@Data
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name; // كرسي متحرك، جهاز أكسجين، إلخ
    private String type;
    private String description;
    private Integer quantityAvailable;

    private String location;
    private String contactPhone;
    private String ngoName; // اسم المنظمة اللي قدمت المعدة
    private boolean fromNgo = false;
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private User provider; // مستشفى، صيدلية، متبرع

    private LocalDateTime listedAt = LocalDateTime.now();

    private boolean lendable = true; // هل يمكن إعارته أم تبرع نهائي
}