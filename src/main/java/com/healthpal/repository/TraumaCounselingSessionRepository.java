// src/main/java/com/healthpal/repository/TraumaCounselingSessionRepository.java
package com.healthpal.repository;

import com.healthpal.entity.TraumaCounselingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TraumaCounselingSessionRepository extends JpaRepository<TraumaCounselingSession, Integer> {
    List<TraumaCounselingSession> findByStatus(String status);
}