package com.toolsai.organization_service.dto.response;

import lombok.Data;

@Data
public class UserResponseDTO {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
