package com.toolsai.user_service.dto;

import com.toolsai.user_service.modal.Role;
import lombok.Data;

@Data
public class ResponseUserDTO {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private boolean banned;
}
