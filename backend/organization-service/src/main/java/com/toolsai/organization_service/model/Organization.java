package com.toolsai.organization_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "organizations")
@Data
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    private String description;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String slug;

    private String logoUrl;

    @ElementCollection
    private List<String> moderators;

    @ElementCollection
    private List<String> subscribers;

    @ElementCollection
    private List<String> tools;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}