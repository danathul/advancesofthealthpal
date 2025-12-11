// src/main/java/com/healthpal/repository/HealthAlertRepository.java
package com.healthpal.repository;

import com.healthpal.entity.HealthAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HealthAlertRepository extends JpaRepository<HealthAlert, Integer> {
    List<HealthAlert> findByIsActiveTrueOrderByPublishedAtDesc();
}