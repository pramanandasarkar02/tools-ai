package com.toolsai.organization_service.repository;

import com.toolsai.organization_service.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, String> {
}
