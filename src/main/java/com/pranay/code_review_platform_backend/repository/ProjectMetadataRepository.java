package com.pranay.code_review_platform_backend.repository;

import com.pranay.code_review_platform_backend.parser.model.ProjectMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectMetadataRepository
        extends JpaRepository<ProjectMetadata, Long> {

    Optional<ProjectMetadata> findByRepositoryId(Long repositoryId);
}