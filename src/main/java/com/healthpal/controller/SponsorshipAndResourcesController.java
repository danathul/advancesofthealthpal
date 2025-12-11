// src/main/java/com/healthpal/controller/SponsorshipAndResourcesController.java
package com.healthpal.controller;

import com.healthpal.dto.*;
import com.healthpal.service.SponsorshipAndResourcesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Group 2: Financial & Resource Support")
@RestController
@RequestMapping("/api/v1/sponsorship-resources")
@RequiredArgsConstructor
public class SponsorshipAndResourcesController {

    private final SponsorshipAndResourcesService service;

    // ==== Medical Sponsorship ====
    @Operation(summary = "Create new treatment sponsorship case")
    @PostMapping("/cases")
    public ResponseEntity<TreatmentCaseDTO> createCase(@RequestBody TreatmentCaseDTO dto) {
        return ResponseEntity.ok(service.createTreatmentCase(dto));
    }

    @Operation(summary = "Get all open cases")
    @GetMapping("/cases")
    public ResponseEntity<List<TreatmentCaseDTO>> getOpenCases() {
        return ResponseEntity.ok(service.getOpenCases());
    }

    @Operation(summary = "Get case details + donations")
    @GetMapping("/cases/{id}")
    public ResponseEntity<TreatmentCaseDTO> getCase(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getCaseWithProgress(id));
    }

    @Operation(summary = "Donate to a case")
    @PostMapping("/donations")
    public ResponseEntity<DonationDTO> donate(@RequestBody DonationDTO dto) {
        return ResponseEntity.ok(service.makeDonation(dto));
    }

    @Operation(summary = "Get donations for a case")
    @GetMapping("/cases/{caseId}/donations")
    public ResponseEntity<List<DonationDTO>> getDonations(@PathVariable Integer caseId) {
        return ResponseEntity.ok(service.getDonationsForCase(caseId));
    }

    // ==== Medication & Equipment ====
    @Operation(summary = "Request medicine delivery")
    @PostMapping("/medicine-requests")
    public ResponseEntity<MedicineRequestDTO> requestMedicine(@RequestBody MedicineRequestDTO dto) {
        return ResponseEntity.ok(service.createMedicineRequest(dto));
    }

    @Operation(summary = "List all pending medicine requests")
    @GetMapping("/medicine-requests/pending")
    public ResponseEntity<List<MedicineRequestDTO>> getPendingMedicineRequests() {
        return ResponseEntity.ok(service.getPendingMedicineRequests());
    }

    @Operation(summary = "Mark medicine request as fulfilled")
    @PutMapping("/medicine-requests/{id}/fulfill")
    public ResponseEntity<MedicineRequestDTO> fulfillMedicineRequest(@PathVariable Integer id, @RequestParam Integer fulfilledById) {
        return ResponseEntity.ok(service.fulfillMedicineRequest(id, fulfilledById));
    }

    @Operation(summary = "List available equipment")
    @GetMapping("/equipment")
    public ResponseEntity<List<EquipmentDTO>> getAvailableEquipment() {
        return ResponseEntity.ok(service.getAvailableEquipment());
    }

    @Operation(summary = "Add new equipment to inventory")
    @PostMapping("/equipment")
    public ResponseEntity<EquipmentDTO> addEquipment(@RequestBody EquipmentDTO dto) {
        return ResponseEntity.ok(service.addEquipment(dto));
    }
}