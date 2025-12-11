// src/main/java/com/healthpal/controller/HealthAlertController.java
package com.healthpal.controller;

import com.healthpal.entity.HealthAlert;
import com.healthpal.repository.HealthAlertRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Group 3: Health Education & Public Health Alerts")
@RestController
@RequestMapping("/api/v1/alerts")
@RequiredArgsConstructor
public class HealthAlertController {

    private final HealthAlertRepository alertRepo;

    @Operation(summary = "Get all active public health alerts")
    @GetMapping
    public ResponseEntity<List<HealthAlert>> getActiveAlerts() {
        return ResponseEntity.ok(alertRepo.findByIsActiveTrueOrderByPublishedAtDesc());
    }
}