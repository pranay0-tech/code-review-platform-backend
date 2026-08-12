package com.pranay.code_review_platform_backend.repository;

import com.pranay.code_review_platform_backend.parser.model.PackageMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PackageMetadataRepository
        extends JpaRepository<PackageMetadata, Long> {
                long countByRepositoryId(Long repositoryId);

    Optional<PackageMetadata> findByRepositoryIdAndPackageName(
            Long repositoryId, 
            String packageName
    );
}
