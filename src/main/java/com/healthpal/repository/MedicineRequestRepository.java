package com.healthpal.repository;

import com.healthpal.entity.MedicineRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineRequestRepository extends JpaRepository<MedicineRequest, Integer> {

    List<MedicineRequest> findByStatus(String status);
}