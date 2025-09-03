package com.toolsai.organization_service.controller;


import com.toolsai.organization_service.dto.request.OrganizationRequestDTO;
import com.toolsai.organization_service.dto.response.OrganizationResponseDTO;
import com.toolsai.organization_service.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<OrganizationResponseDTO> createOrganization(@RequestBody OrganizationRequestDTO organizationRequestDTO, @RequestHeader("adminId") String adminId) {
        return ResponseEntity.ok(organizationService.createOrganization(organizationRequestDTO, adminId));
    }

    @PutMapping
    public ResponseEntity<OrganizationResponseDTO> updateOrganization(@RequestBody OrganizationRequestDTO organizationRequestDTO, @RequestHeader("adminId") String adminId) {
        return ResponseEntity.ok(organizationService.updateOrganization(organizationRequestDTO, adminId));
    }

    @DeleteMapping("/{organizationId}")
    public ResponseEntity<OrganizationResponseDTO> deleteOrganization(@PathVariable  String organizationId, @RequestHeader("adminId") String adminId) {
        return ResponseEntity.ok(organizationService.deleteOrganization(organizationId, adminId));
    }

    @GetMapping("/{organizationId}")
    public ResponseEntity<OrganizationResponseDTO> getOrganization(@PathVariable String organizationId) {
        return ResponseEntity.ok(organizationService.getOrganizationById(organizationId));
    }

    @GetMapping
    public ResponseEntity<List<OrganizationResponseDTO>> getAllOrganizations() {
        return ResponseEntity.ok(organizationService.getAllOrganizations());
    }

}
