package com.healthpal.repository;

import com.healthpal.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Integer> {
    List<Donation> findByTreatmentCaseId(Integer treatmentCaseId);
}