package com.toolsai.organization_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "models")
@Data
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    private String description;

    @ElementCollection
    private List<String> features;

    @DecimalMin("0.0")
    private BigDecimal pricing;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "ai_tool_id", nullable = false)
    private AITool aiTool;

    @ElementCollection
    private Map<String, Boolean> releaseVersions;

    private String latestReleaseVersion;

    @Enumerated(EnumType.STRING)
    private ModelStatus status;

    private LocalDateTime releaseDate;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}