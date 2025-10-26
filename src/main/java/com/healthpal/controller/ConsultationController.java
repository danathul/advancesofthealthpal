package com.healthpal.controller;

import com.healthpal.dto.*;
import com.healthpal.service.ConsultationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Group 1: Patient Care & Consultations")
@RestController
@RequestMapping("/api/v1/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService service;

    @Operation(summary = "Book appointment")
    @PostMapping("/appointments")
    public ResponseEntity<AppointmentDTO> book(@RequestBody AppointmentDTO dto) {
        return ResponseEntity.ok(service.bookAppointment(dto));
    }

    @Operation(summary = "Get appointment")
    @GetMapping("/appointments/{id}")
    public ResponseEntity<AppointmentDTO> get(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getAppointment(id));
    }

    @Operation(summary = "Send message")
    @PostMapping("/messages")
    public ResponseEntity<MessageDTO> send(@RequestBody MessageDTO dto) {
        return ResponseEntity.ok(service.sendMessage(dto));
    }

    @Operation(summary = "Get messages")
    @GetMapping("/appointments/{id}/messages")
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getMessagesForAppointment(id));
    }

    @Operation(summary = "Translate text")
    @PostMapping("/translate")
    public ResponseEntity<String> translate(@RequestBody TranslationDTO dto) {
        return ResponseEntity.ok(service.translateMessage(dto));
    }

    @Operation(summary = "Create support group")
    @PostMapping("/support-groups")
    public ResponseEntity<SupportGroupDTO> createGroup(@RequestBody SupportGroupDTO dto) {
        return ResponseEntity.ok(service.createSupportGroup(dto));
    }

    @Operation(summary = "Join group")
    @PostMapping("/support-groups/{groupId}/join")
    public ResponseEntity<Void> join(@PathVariable Integer groupId, @RequestParam Integer userId) {
        service.joinSupportGroup(groupId, userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Post group message")
    @PostMapping("/support-groups/{groupId}/messages")
    public ResponseEntity<MessageDTO> postGroupMsg(@PathVariable Integer groupId, @RequestBody MessageDTO dto) {
        return ResponseEntity.ok(service.postGroupMessage(groupId, dto));
    }

    @Operation(summary = "Get group messages")
    @GetMapping("/support-groups/{groupId}/messages")
    public ResponseEntity<List<MessageDTO>> getGroupMsgs(@PathVariable Integer groupId) {
        return ResponseEntity.ok(service.getGroupMessages(groupId));
    }
}