package com.pranay.code_review_platform_backend.repository;

import com.pranay.code_review_platform_backend.parser.model.MethodMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MethodMetadataRepository
        extends JpaRepository<MethodMetadata, Long> {
}