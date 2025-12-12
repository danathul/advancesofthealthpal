package com.healthpal.controller;

import com.healthpal.dto.LoginRequestDTO;
import com.healthpal.dto.RegisterRequestDTO;
import com.healthpal.dto.UserDTO;
import com.healthpal.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Authentication & User Management")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register new user")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterRequestDTO dto) {  // ← Added @Valid
        return ResponseEntity.ok(authService.register(dto));
    }

    @Operation(summary = "Login user")
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginRequestDTO dto) {  // ← Added @Valid
        return ResponseEntity.ok(authService.login(dto));
    }

    @Operation(summary = "Get user profile")
    @GetMapping("/profile/{id}")
    public ResponseEntity<UserDTO> getProfile(@PathVariable Integer id) {
        return ResponseEntity.ok(authService.getUserProfile(id));
    }

    @Operation(summary = "Update user profile")
    @PutMapping("/profile/{id}")
    public ResponseEntity<UserDTO> updateProfile(
            @PathVariable Integer id,
            @RequestBody UserDTO dto) {
        return ResponseEntity.ok(authService.updateProfile(id, dto));
    }

    @Operation(summary = "Get all users (Admin)")
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(authService.getAllUsers());
    }
}