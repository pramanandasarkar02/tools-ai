package com.toolsai.organization_service.repository;

import com.toolsai.organization_service.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository  extends JpaRepository<Organization, String> {
    Optional<Organization> findOrganizationBySlug(String slug);
    Optional<Organization> findOrganizationById(String id);
}
