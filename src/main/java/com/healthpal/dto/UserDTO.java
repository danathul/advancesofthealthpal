package com.healthpal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "User profile")
public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private String role;
    private String phone;
}