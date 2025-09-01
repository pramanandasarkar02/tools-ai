package com.toolsai.user_service.dto;

import com.toolsai.user_service.modal.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestUserDTO {
    @NotNull(message = "Username is required")
    private String username;
    private String firstName;
    private String lastName;

    @NotNull(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;

    @NotNull(message = "Password is required")
    private String password;

    private Role role;
}
