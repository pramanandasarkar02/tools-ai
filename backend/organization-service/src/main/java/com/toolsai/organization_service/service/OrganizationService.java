package com.toolsai.organization_service.service;

import com.toolsai.organization_service.dto.request.OrganizationRequestDTO;
import com.toolsai.organization_service.dto.response.AIToolResponseDTO;
import com.toolsai.organization_service.dto.response.OrganizationResponseDTO;
import com.toolsai.organization_service.dto.response.UserResponseDTO;

import java.util.List;

public interface OrganizationService {
    OrganizationResponseDTO createOrganization(OrganizationRequestDTO organizationRequestDTO, String adminId);
    OrganizationResponseDTO updateOrganization(OrganizationRequestDTO organizationRequestDTO);
    OrganizationResponseDTO deleteOrganization(String organizationId, String adminId) throws Exception;
    OrganizationResponseDTO getOrganizationById(String organizationId);
    OrganizationResponseDTO getOrganizationBySlug(String slug);
    List<OrganizationResponseDTO> getAllOrganizations();

    List<UserResponseDTO> getModerators(String organizationId);
    List<UserResponseDTO> getSubscribers(String organizationId);
    
    List<AIToolResponseDTO> getTools(String organizationId);
}
