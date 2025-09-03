package com.toolsai.organization_service.service.impl;

import com.toolsai.organization_service.dto.request.AIToolRequestDTO;
import com.toolsai.organization_service.dto.response.AIToolResponseDTO;
import com.toolsai.organization_service.service.AIToolService;

import java.util.List;

public class AIToolServiceImpl implements AIToolService {
    @Override
    public AIToolResponseDTO createAITool(AIToolRequestDTO aIToolRequestDTO, String adminId) {
        return null;
    }

    @Override
    public AIToolResponseDTO updateAITool(AIToolRequestDTO aIToolRequestDTO, String adminId) {
        return null;
    }

    @Override
    public AIToolResponseDTO deleteAITool(String aIToolId, String adminId) {
        return null;
    }

    @Override
    public AIToolResponseDTO getAIToolById(String aIToolId) {
        return null;
    }

    @Override
    public AIToolResponseDTO getAIToolBySlug(String aIToolSlug) {
        return null;
    }

    @Override
    public List<AIToolResponseDTO> getAllAITools(String adminId) {
        return List.of();
    }
}
