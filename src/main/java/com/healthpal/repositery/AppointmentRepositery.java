package com.healthpal.repositery;

import com.healthpal.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepositery extends JpaRepository<Appointment, Long> {}