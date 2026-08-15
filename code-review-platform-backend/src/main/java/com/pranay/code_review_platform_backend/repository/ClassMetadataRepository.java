package com.pranay.code_review_platform_backend.repository;

import com.pranay.code_review_platform_backend.parser.model.ClassMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassMetadataRepository extends JpaRepository<ClassMetadata, Long> {
    long countByRepositoryId(Long repositoryId);
}
