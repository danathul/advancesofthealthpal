package com.healthpal.service;

import com.healthpal.dto.LoginRequestDTO;
import com.healthpal.dto.RegisterRequestDTO;
import com.healthpal.dto.UserDTO;
import java.util.List;

public interface AuthService {
    UserDTO register(RegisterRequestDTO dto);
    UserDTO login(LoginRequestDTO dto);
    UserDTO getUserProfile(Integer id);
    UserDTO updateProfile(Integer id, UserDTO dto);
    List<UserDTO> getAllUsers();
}