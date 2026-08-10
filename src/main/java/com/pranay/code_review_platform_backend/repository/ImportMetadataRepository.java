package com.pranay.code_review_platform_backend.repository;

import com.pranay.code_review_platform_backend.parser.model.ImportMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportMetadataRepository
        extends JpaRepository<ImportMetadata, Long> {
}
