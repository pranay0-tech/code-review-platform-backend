package com.pranay.code_review_platform_backend.repository;

import com.pranay.code_review_platform_backend.parser.model.TechnologyMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TechnologyMetadataRepository
        extends JpaRepository<TechnologyMetadata, Long> {

    Optional<TechnologyMetadata> findByRepositoryIdAndTechnologyName(
            Long repositoryId,
            String technologyName
    );

    List<TechnologyMetadata> findByRepositoryId(
            Long repositoryId
    );
}
