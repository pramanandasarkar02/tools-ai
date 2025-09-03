package com.toolsai.organization_service.service.impl;

import com.toolsai.organization_service.dto.request.ModelRequestDTO;
import com.toolsai.organization_service.dto.response.ModelResponseDTO;
import com.toolsai.organization_service.service.ModelService;

import java.util.List;

public class ModelServiceImpl implements ModelService {
    @Override
    public ModelResponseDTO createModel(ModelRequestDTO modelRequestDTO, String adminId) {
        return null;
    }

    @Override
    public ModelResponseDTO updateModel(ModelRequestDTO modelRequestDTO, String adminId) {
        return null;
    }

    @Override
    public ModelResponseDTO deleteModel(String modelId, String adminId) {
        return null;
    }

    @Override
    public ModelResponseDTO getModelById(String modelId) {
        return null;
    }

    @Override
    public ModelResponseDTO getModelBySlug(String modelSlug) {
        return null;
    }

    @Override
    public List<ModelResponseDTO> getAllModels(String adminId) {
        return List.of();
    }

    @Override
    public List<ModelResponseDTO> getActiveModels(String aIToolId) {
        return List.of();
    }

    @Override
    public List<ModelResponseDTO> getInactiveModels(String aIToolId) {
        return List.of();
    }

    @Override
    public List<ModelResponseDTO> getFreemiumModels(String aIToolId) {
        return List.of();
    }

    @Override
    public List<ModelResponseDTO> getPaidModels(String aIToolId) {
        return List.of();
    }

    @Override
    public List<ModelResponseDTO> getModelsByOrganizationId(String organizationId) {
        return List.of();
    }

    @Override
    public List<ModelResponseDTO> getModelsByAIToolId(String aIToolId) {
        return List.of();
    }

    @Override
    public List<ModelResponseDTO> getModelsByOrganizationIdAndAIToolId(String organizationId, String aIToolId) {
        return List.of();
    }
}
