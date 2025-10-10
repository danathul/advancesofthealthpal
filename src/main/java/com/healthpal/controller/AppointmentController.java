package com.healthpal.controller;

import com.healthpal.model.Appointment;
import com.healthpal.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService service;

    @PostMapping("/book")
    public Appointment book(@RequestBody Appointment appt) {
        return service.bookAppointment(appt);
    }
}
