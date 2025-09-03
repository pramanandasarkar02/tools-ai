package com.toolsai.organization_service.service;

import com.toolsai.organization_service.dto.request.AIToolRequestDTO;
import com.toolsai.organization_service.dto.request.ModelRequestDTO;
import com.toolsai.organization_service.dto.response.AIToolResponseDTO;

import java.util.List;

public interface AIToolService{
    AIToolResponseDTO createAITool(AIToolRequestDTO aIToolRequestDTO, String adminId);
    AIToolResponseDTO updateAITool(AIToolRequestDTO aIToolRequestDTO, String adminId);
    AIToolResponseDTO deleteAITool(String aIToolId, String adminId);
    AIToolResponseDTO getAIToolById(String aIToolId);
    AIToolResponseDTO getAIToolBySlug(String aIToolSlug);
    List<AIToolResponseDTO> getAllAITools(String adminId);



}
