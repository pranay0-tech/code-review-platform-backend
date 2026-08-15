package com.pranay.code_review_platform_backend.parser.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "technology_metadata")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechnologyMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long repositoryId;

    private String technologyName;
}
