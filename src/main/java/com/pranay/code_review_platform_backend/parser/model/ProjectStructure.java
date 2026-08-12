package com.pranay.code_review_platform_backend.parser.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "project_structures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "repository_id", nullable = false, unique = true)
    private Long repositoryId;

    // Stores the directory hierarchy as a JSON string
    @Column(name = "structure_json", columnDefinition = "TEXT", nullable = false)
    private String structureJson;
}