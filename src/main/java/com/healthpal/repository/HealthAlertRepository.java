// src/main/java/com/healthpal/repository/HealthAlertRepository.java
package com.healthpal.repository;

import com.healthpal.entity.HealthAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface HealthAlertRepository extends JpaRepository<HealthAlert, Integer> {
    List<HealthAlert> findByActiveTrueOrderByPublishedAtDesc();
}