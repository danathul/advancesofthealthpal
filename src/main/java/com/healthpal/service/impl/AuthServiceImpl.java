package com.healthpal.service.impl;

import com.healthpal.dto.LoginRequestDTO;
import com.healthpal.dto.RegisterRequestDTO;
import com.healthpal.dto.UserDTO;
import com.healthpal.entity.*;
import com.healthpal.repository.*;
import com.healthpal.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepo;
    private final PatientRepository patientRepo;
    private final DoctorRepository doctorRepo;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public UserDTO register(RegisterRequestDTO dto) {
        logger.info("Registering new user: {}", dto.getEmail());

        if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            logger.warn("Registration failed: Email already exists - {}", dto.getEmail());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhone(dto.getPhone());

        try {
            user.setRole(User.Role.valueOf(dto.getRole().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid role");
        }

        User savedUser = userRepo.save(user);

        if (user.getRole() == User.Role.PATIENT) {
            Patient patient = new Patient();
            patient.setUser(savedUser);
            patient.setAge(dto.getAge());
            if (dto.getGender() != null) {
                patient.setGender(Patient.Gender.valueOf(dto.getGender()));
            }
            patientRepo.save(patient);
            logger.info("Patient profile created for user: {}", savedUser.getId());
        } else if (user.getRole() == User.Role.DOCTOR) {
            Doctor doctor = new Doctor();
            doctor.setUser(savedUser);
            doctor.setSpecialization(dto.getSpecialization());
            doctor.setLicenseNo(dto.getLicenseNo());
            doctorRepo.save(doctor);
            logger.info("Doctor profile created for user: {}", savedUser.getId());
        }

        logger.info("User registered successfully: {}", savedUser.getEmail());
        return mapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO login(LoginRequestDTO dto) {
        logger.info("Login attempt for email: {}", dto.getEmail());

        User user = userRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> {
                    logger.warn("Login failed: User not found - {}", dto.getEmail());
                    return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
                });

        if (!user.getPassword().equals(dto.getPassword())) {
            logger.warn("Login failed: Wrong password for - {}", dto.getEmail());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        logger.info("User logged in successfully: {}", user.getEmail());
        return mapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO getUserProfile(Integer id) {
        logger.info("Fetching profile for user ID: {}", id);
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return mapper.map(user, UserDTO.class);
    }

    @Override
    @Transactional
    public UserDTO updateProfile(Integer id, UserDTO dto) {
        logger.info("Updating profile for user ID: {}", id);

        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getPhone() != null) user.setPhone(dto.getPhone());

        User updated = userRepo.save(user);
        logger.info("Profile updated for user: {}", updated.getEmail());
        return mapper.map(updated, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        logger.info("Fetching all users");
        return userRepo.findAll().stream()
                .map(u -> mapper.map(u, UserDTO.class))
                .collect(Collectors.toList());
    }
}