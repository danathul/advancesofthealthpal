package com.healthpal.repository;

import com.healthpal.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByAppointmentId(Integer appointmentId);
}