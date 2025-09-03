package com.toolsai.organization_service.repository;

import com.toolsai.organization_service.model.AITool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AIToolRepository extends JpaRepository<AITool, String> {
}
