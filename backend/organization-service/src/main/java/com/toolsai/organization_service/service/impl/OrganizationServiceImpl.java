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

        Boolean isAdmin = restTemplate.getForObject(USER_SERVICE_URL + "admin/" + adminId, Boolean.class);
        if (!isAdmin) {
            throw new EntityNotFoundException("User is not admin");
        }
        UserResponseDTO userResponseDTO = restTemplate.getForObject(USER_SERVICE_URL + organizationRequestDTO.getModeratorId(), UserResponseDTO.class);
        Boolean isModerator = restTemplate.getForObject(USER_SERVICE_URL + "moderator/" + userResponseDTO.getId(), Boolean.class);
        if (!isModerator) {
            throw new EntityNotFoundException("User is not moderator");
        }

        try{
            Organization organization = new Organization();
            organization.setName(organizationRequestDTO.getName());
            organization.setSlug(organizationRequestDTO.getSlug());
            organization.setDescription(organizationRequestDTO.getDescription());
            organization.setLogoUrl(organizationRequestDTO.getLogoUrl());
            organization.setModerators(List.of(userResponseDTO.getId()));
            organization.setSubscribers(List.of(userResponseDTO.getId()));
            organization.setTools(List.of());
            organizationRepository.save(organization);
            return convertToOrganizationResponseDTO(organization);
        }
        catch (Exception e){
            throw new EntityNotFoundException("User is not moderator");
        }
    }

    @Override
    public OrganizationResponseDTO updateOrganization(OrganizationRequestDTO organizationRequestDTO) {

        String moderatorId = organizationRequestDTO.getModeratorId();
        Boolean isModerator = restTemplate.getForObject(USER_SERVICE_URL + "moderator/" + moderatorId, Boolean.class);
        if (!isModerator) {
            throw new EntityNotFoundException("User is not moderator");
        }
        try{
            Organization organization = organizationRepository.findOrganizationBySlug(organizationRequestDTO.getSlug()).get();
            organization.setName(organizationRequestDTO.getName());
            organization.setSlug(organizationRequestDTO.getSlug());
            organization.setDescription(organizationRequestDTO.getDescription());
            organization.setModerators(List.of(moderatorId));
            organization.setSubscribers(List.of(moderatorId));
            organizationRepository.save(organization);
            return convertToOrganizationResponseDTO(organization);
        }catch (Exception e){
            throw new EntityNotFoundException("Organization not found");
        }

    }

    @Override
    public OrganizationResponseDTO deleteOrganization(String organizationId, String adminId) throws Exception {
        log.info("Deleting organization with id: {}", organizationId);
        Boolean isAdmin = restTemplate.getForObject(USER_SERVICE_URL + "admin/" + adminId, Boolean.class);
        if (!isAdmin) {
            throw new EntityNotFoundException("User is not admin");
        }
        // Fetch the organization
        Optional<Organization> optionalOrganization = organizationRepository.findOrganizationById(organizationId);
        if (optionalOrganization.isEmpty()) {
            throw new EntityNotFoundException("Organization with id " + organizationId + " not found");
        }
        try{
            // Get the organization and delete it
            Organization organization = optionalOrganization.get();
            organizationRepository.delete(organization);

            // Convert to DTO and return
            return convertToOrganizationResponseDTO(organization);
        } catch (Exception e){
            throw new Exception("Failed to delete organization");
        }


    }

    @Override
    public OrganizationResponseDTO getOrganizationById(String organizationId) {
        Optional<Organization> optionalOrganization = organizationRepository.findOrganizationById(organizationId);
        if(optionalOrganization.isEmpty()){
            throw new EntityNotFoundException("Organization with id " + organizationId + " not found");

        }
        Organization organization = optionalOrganization.get();
        try{
            return convertToOrganizationResponseDTO(organization);
        } catch (Exception e){
            throw new EntityNotFoundException("Failed to get organization");
        }

    }

    @Override
    public OrganizationResponseDTO getOrganizationBySlug(String slug) {
        Optional<Organization> optionalOrganization = organizationRepository.findOrganizationBySlug(slug);
        if(optionalOrganization.isEmpty()){
            throw new EntityNotFoundException("Organization with slug " + slug + " not found");
        }
        Organization organization = optionalOrganization.get();
        try{
            return convertToOrganizationResponseDTO(organization);
        } catch (Exception e){
            throw new EntityNotFoundException("Failed to get organization");
        }
    }

    @Override
    public List<OrganizationResponseDTO> getAllOrganizations() {
        log.info("Getting all organizations");
        try{
            List<Organization> organizations = organizationRepository.findAll();
            log.info("All organizations fetched successfully");
            for (Organization organization : organizations) {
                log.info("Organization: " + organization.getName());
            }
            List<OrganizationResponseDTO> organizationResponseDTOList = organizations.stream().map(this::convertToOrganizationResponseDTO).collect(Collectors.toList());
            log.info("All organizations fetched2 successfully");
            return organizationResponseDTOList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserResponseDTO> getModerators(String organizationId) {
        try{
            List<String> moderatorIds = organizationRepository.findOrganizationById(organizationId).get().getModerators();
            List<UserResponseDTO> userResponseDTOList = moderatorIds
                    .stream()
                    .map(id -> restTemplate.getForObject(USER_SERVICE_URL + id, UserResponseDTO.class)).collect(Collectors.toList());
            return userResponseDTOList;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<UserResponseDTO> getSubscribers(String organizationId) {
        try{
            List<String> subscriberIds = organizationRepository.findOrganizationById(organizationId).get().getSubscribers();
//        call user service to get user details
            List<UserResponseDTO> userResponseDTOList = subscriberIds.stream().map(id -> restTemplate.getForObject(USER_SERVICE_URL + id, UserResponseDTO.class)).collect(Collectors.toList());
            return userResponseDTOList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


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
        organizationResponseDTO.setLogoUrl(organization.getLogoUrl());
        organizationResponseDTO.setDescription(organization.getDescription());
        organizationResponseDTO.setModerators(getModerators(organization.getId()));
        organizationResponseDTO.setSubscribers(getSubscribers(organization.getId()));
        organizationResponseDTO.setAiTools(organization.getTools());
        organizationResponseDTO.setCreatedAt(organization.getCreatedAt());
        organizationResponseDTO.setUpdatedAt(organization.getUpdatedAt());
        return organizationResponseDTO;
    }
}
