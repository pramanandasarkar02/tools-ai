package com.toolsai.organization_service.service.impl;

import com.toolsai.organization_service.dto.request.OrganizationRequestDTO;
import com.toolsai.organization_service.dto.response.AIToolResponseDTO;
import com.toolsai.organization_service.dto.response.OrganizationResponseDTO;
import com.toolsai.organization_service.dto.response.UserResponseDTO;
import com.toolsai.organization_service.model.Organization;
import com.toolsai.organization_service.repository.OrganizationRepository;
import com.toolsai.organization_service.service.OrganizationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final RestTemplate restTemplate;
    private final String USER_SERVICE_URL = "http://localhost:8081/api/v1/user/";

    @Override
    public OrganizationResponseDTO createOrganization(OrganizationRequestDTO organizationRequestDTO, String adminId) {

        UserResponseDTO userResponseDTO = restTemplate.getForObject(USER_SERVICE_URL + organizationRequestDTO.getModeratorId(), UserResponseDTO.class);
        log.info("UserResponseDTO: {}", userResponseDTO);


//        check user is admin or not


        Organization organization = new Organization();
        organization.setName(organizationRequestDTO.getName());
        organization.setSlug(organizationRequestDTO.getSlug());
        organization.setDescription(organizationRequestDTO.getDescription());
        organization.setModerators(List.of(userResponseDTO.getId()));
        organization.setSubscribers(List.of(userResponseDTO.getId()));
        organization.setTools(List.of());
        organizationRepository.save(organization);
        return convertToOrganizationResponseDTO(organization);
    }

    @Override
    public OrganizationResponseDTO updateOrganization(OrganizationRequestDTO organizationRequestDTO, String adminId) {
        Organization organization = organizationRepository.findOrganizationBySlug(organizationRequestDTO.getSlug()).get();
        organization.setName(organizationRequestDTO.getName());
        organization.setSlug(organizationRequestDTO.getSlug());
        organization.setDescription(organizationRequestDTO.getDescription());
        organization.setModerators(List.of(adminId));
        organization.setSubscribers(List.of(adminId));
        organization.setTools(List.of());
        organizationRepository.save(organization);
        return convertToOrganizationResponseDTO(organization);
    }

    @Override
    public OrganizationResponseDTO deleteOrganization(String organizationId, String adminId) {
        log.info("Deleting organization with id: {}", organizationId);

        // Fetch the organization
        Optional<Organization> optionalOrganization = organizationRepository.findOrganizationById(organizationId);

        // Check if the organization exists
        if (optionalOrganization.isEmpty()) {
            log.error("Organization with id {} not found", organizationId);
            throw new EntityNotFoundException("Organization with id " + organizationId + " not found");
        }

        // Get the organization and delete it
        Organization organization = optionalOrganization.get();
        organizationRepository.delete(organization);

        // Convert to DTO and return
        return convertToOrganizationResponseDTO(organization);
    }

    @Override
    public OrganizationResponseDTO getOrganizationById(String organizationId) {
        Organization organization = organizationRepository.findOrganizationById(organizationId).get();
//        find moderators
        List<UserResponseDTO> moderators = getModerators(organizationId);
//        find subscribers
        List<UserResponseDTO> subscribers = getSubscribers(organizationId);
//        find tools
        List<AIToolResponseDTO> tools = getTools(organizationId);


        return convertToOrganizationResponseDTO(organization);
    }

    @Override
    public OrganizationResponseDTO getOrganizationBySlug(String slug) {
        Organization organization = organizationRepository.findOrganizationBySlug(slug).get();
//        find moderators
        List<UserResponseDTO> moderators = getModerators(organization.getId());
//        find subscribers
        List<UserResponseDTO> subscribers = getSubscribers(organization.getId());
//        find tools
        List<AIToolResponseDTO> tools = getTools(organization.getId());


        return convertToOrganizationResponseDTO(organization);
    }

    @Override
    public List<OrganizationResponseDTO> getAllOrganizations() {

        List<Organization> organizations = organizationRepository.findAll();
        List<OrganizationResponseDTO> organizationResponseDTOList = organizations.stream().map(this::convertToOrganizationResponseDTO).collect(Collectors.toList());
        return organizationResponseDTOList;
    }

    @Override
    public List<UserResponseDTO> getModerators(String organizationId) {
        List<String> moderatorIds = organizationRepository.findOrganizationById(organizationId).get().getModerators();
//        call user service to get user details
        List<UserResponseDTO> userResponseDTOList = moderatorIds.stream().map(id -> restTemplate.getForObject(USER_SERVICE_URL + id, UserResponseDTO.class)).collect(Collectors.toList());







        return userResponseDTOList;
    }

    @Override
    public List<UserResponseDTO> getSubscribers(String organizationId) {
        List<String> subscriberIds = organizationRepository.findOrganizationById(organizationId).get().getSubscribers();
//        call user service to get user details
        List<UserResponseDTO> userResponseDTOList = subscriberIds.stream().map(id -> restTemplate.getForObject(USER_SERVICE_URL + id, UserResponseDTO.class)).collect(Collectors.toList());





        return userResponseDTOList;
    }

    @Override
    public List<AIToolResponseDTO> getTools(String organizationId) {
        return List.of();
    }


    private OrganizationResponseDTO convertToOrganizationResponseDTO(Organization organization) {
        OrganizationResponseDTO organizationResponseDTO = new OrganizationResponseDTO();
        organizationResponseDTO.setOrganizationId(organization.getId());
        organizationResponseDTO.setName(organization.getName());
        organizationResponseDTO.setSlug(organization.getSlug());
        organizationResponseDTO.setDescription(organization.getDescription());
        organizationResponseDTO.setModerators(getModerators(organization.getId()));
        organizationResponseDTO.setSubscribers(getSubscribers(organization.getId()));
        organizationResponseDTO.setAiTools(organization.getTools());
        return organizationResponseDTO;
    }
}
