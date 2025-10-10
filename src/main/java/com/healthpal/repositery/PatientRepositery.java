package com.healthpal.repositery;

import com.healthpal.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepositery extends JpaRepository<Patient, Long> {}

