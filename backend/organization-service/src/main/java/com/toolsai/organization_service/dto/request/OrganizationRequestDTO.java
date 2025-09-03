package com.toolsai.organization_service.dto.request;


import lombok.Data;

@Data
public class OrganizationRequestDTO {
    private String name;
    private String description;
    private String slug;
    private String logoUrl;
    private String moderatorId;
}
