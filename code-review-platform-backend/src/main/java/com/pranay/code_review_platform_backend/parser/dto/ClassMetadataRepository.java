package com.pranay.code_review_platform_backend.parser.dto;

import com.pranay.code_review_platform_backend.parser.model.ClassMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ClassMetadataRepository extends JpaRepository<ClassMetadata, Long> {

    // Retained existing method
    long countByRepositoryId(Long repositoryId);

    // Count specific component types (CONTROLLER, SERVICE, REPOSITORY, ENTITY)
    long countByRepositoryIdAndClassType(Long repositoryId, String classType);

    // Calculate total methods across all classes for a repository
    @Query("""
           SELECT COALESCE(SUM(c.methodCount), 0)
           FROM ClassMetadata c
           WHERE c.repositoryId = :repositoryId
           """)
    long countTotalMethods(@Param("repositoryId") Long repositoryId);

    // Calculate total parsed classes for a repository
    @Query("""
           SELECT COUNT(c)
           FROM ClassMetadata c
           WHERE c.repositoryId = :repositoryId
           """)
    long countTotalClasses(@Param("repositoryId") Long repositoryId);

    // Delete metadata when re-indexing a repository
    void deleteByRepositoryId(Long repositoryId);
}