package com.healthpal.repository;

import com.healthpal.entity.TreatmentCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreatmentCaseRepository extends JpaRepository<TreatmentCase, Integer> {

    // غيّرنا من CaseStatus إلى String
    List<TreatmentCase> findByStatus(String status);
}