package com.toolsai.organization_service.dto.response;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrganizationResponseDTO {
    private String organizationId;
    private String name;
    private String description;
    private String slug;
    private String logoUrl;
    private List<UserResponseDTO> moderators;
    private List<String> aiTools;
    private List<UserResponseDTO> subscribers;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
