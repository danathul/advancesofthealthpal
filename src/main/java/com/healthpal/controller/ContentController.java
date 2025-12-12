package com.healthpal.controller;

import com.healthpal.dto.*;
import com.healthpal.entity.*;
import com.healthpal.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//rahaf's part
@Tag(name = "Group 3: Health Content & Community Events")
@RestController
@RequestMapping("/api/v1/content")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService service;

    // Health Guides
    @Operation(summary = "Create new health guide")
    @PostMapping("/guides")
    public ResponseEntity<HealthGuide> createGuide(@RequestBody HealthGuide guide) {
        return ResponseEntity.ok(service.createHealthGuide(guide));
    }

    @Operation(summary = "Get all health guides")
    @GetMapping("/guides")
    public ResponseEntity<List<HealthGuide>> getGuides() {
        return ResponseEntity.ok(service.getAllHealthGuides());
    }

    @Operation(summary = "Get guide by ID")
    @GetMapping("/guides/{id}")
    public ResponseEntity<HealthGuide> getGuide(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getHealthGuideById(id));
    }

    @Operation(summary = "Update health guide")
    @PutMapping("/guides/{id}")
    public ResponseEntity<HealthGuide> updateGuide(@PathVariable Integer id, @RequestBody HealthGuide guide) {
        return ResponseEntity.ok(service.updateHealthGuide(id, guide));  // ← أضيفي هاد
    }

    @Operation(summary = "Delete health guide")
    @DeleteMapping("/guides/{id}")
    public ResponseEntity<Void> deleteGuide(@PathVariable Integer id) {
        service.deleteHealthGuide(id);  // ← أضيفي هاد
        return ResponseEntity.noContent().build();
    }

    // Webinars
    @Operation(summary = "Create new webinar")
    @PostMapping("/webinars")
    public ResponseEntity<Webinar> createWebinar(@RequestBody WebinarCreateDTO dto) {
        return ResponseEntity.ok(service.createWebinar(dto));
    }

    @Operation(summary = "Get all webinars")
    @GetMapping("/webinars")
    public ResponseEntity<List<Webinar>> getWebinars() {
        return ResponseEntity.ok(service.getAllWebinars());
    }

    @Operation(summary = "Get webinar by ID")
    @GetMapping("/webinars/{id}")
    public ResponseEntity<Webinar> getWebinar(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getWebinarById(id));  // ← أضيفي هاد
    }

    // Workshops
    @Operation(summary = "Create new workshop")
    @PostMapping("/workshops")
    public ResponseEntity<Workshop> createWorkshop(@RequestBody WorkshopCreateDTO dto) {
        return ResponseEntity.ok(service.createWorkshop(dto));
    }

    @Operation(summary = "Get all workshops")
    @GetMapping("/workshops")
    public ResponseEntity<List<Workshop>> getWorkshops() {
        return ResponseEntity.ok(service.getAllWorkshops());
    }

    @Operation(summary = "Get workshop by ID")
    @GetMapping("/workshops/{id}")
    public ResponseEntity<Workshop> getWorkshop(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getWorkshopById(id));  // ← أضيفي هاد
    }

    // Medical Missions
    @Operation(summary = "Create new medical mission")
    @PostMapping("/missions")
    public ResponseEntity<MedicalMission> createMission(@RequestBody MedicalMission mission) {
        return ResponseEntity.ok(service.createMedicalMission(mission));
    }

    @Operation(summary = "Get all medical missions")
    @GetMapping("/missions")
    public ResponseEntity<List<MedicalMission>> getMissions() {
        return ResponseEntity.ok(service.getAllMedicalMissions());
    }

    @Operation(summary = "Get medical mission by ID")
    @GetMapping("/missions/{id}")
    public ResponseEntity<MedicalMission> getMission(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getMedicalMissionById(id));  // ← أضيفي هاد
    }

    // Notifications (اختياري - لو بدك تخليه)
    @Operation(summary = "Create notification")
    @PostMapping("/notifications")
    public ResponseEntity<NotificationEntity> createNotification(@RequestBody NotificationEntity notification) {
        return ResponseEntity.ok(service.createNotification(notification));  // ← أضيفي هاد لو بدك
    }
}