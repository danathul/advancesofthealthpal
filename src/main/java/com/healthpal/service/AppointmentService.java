package com.healthpal.service;

import com.healthpal.model.Appointment;
import com.healthpal.repositery.AppointmentRepositery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepositery repo;

    public Appointment bookAppointment(Appointment appt) {
        // check availability, validate data
        return repo.save(appt);
    }
}
