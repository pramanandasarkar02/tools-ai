package com.toolsai.organization_service.service;

import com.toolsai.organization_service.dto.request.ModelRequestDTO;
import com.toolsai.organization_service.dto.response.ModelResponseDTO;

import java.util.List;

public interface ModelService {
    ModelResponseDTO createModel(ModelRequestDTO modelRequestDTO, String adminId);
    ModelResponseDTO updateModel(ModelRequestDTO modelRequestDTO, String adminId);
    ModelResponseDTO deleteModel(String modelId, String adminId);
    ModelResponseDTO getModelById(String modelId);
    ModelResponseDTO getModelBySlug(String modelSlug);
    List<ModelResponseDTO> getAllModels(String adminId);


    List<ModelResponseDTO> getActiveModels(String aIToolId);
    List<ModelResponseDTO> getInactiveModels(String aIToolId);

    List<ModelResponseDTO> getFreemiumModels(String aIToolId);
    List<ModelResponseDTO> getPaidModels(String aIToolId);

    List<ModelResponseDTO> getModelsByOrganizationId(String organizationId);
    List<ModelResponseDTO> getModelsByAIToolId(String aIToolId);

    List<ModelResponseDTO> getModelsByOrganizationIdAndAIToolId(String organizationId, String aIToolId);



}
