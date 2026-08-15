
package com.pranay.code_review_platform_backend.repository;

import com.pranay.code_review_platform_backend.parser.model.ClassMetadata;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClassMetadataRepository extends JpaRepository<ClassMetadata, Long> {
    
    long countByRepositoryId(Long repositoryId);

    long countByRepositoryIdAndClassType(Long repositoryId, String classType);

    @Query("""
           SELECT COALESCE(SUM(c.methodCount), 0)
           FROM ClassMetadata c
           WHERE c.repositoryId = :repositoryId
           """)
    long countTotalMethods(@Param("repositoryId") Long repositoryId);

    @Query("""
           SELECT COUNT(c)
           FROM ClassMetadata c
           WHERE c.repositoryId = :repositoryId
           """)
    long countTotalClasses(@Param("repositoryId") Long repositoryId);

    void deleteByRepositoryId(Long repositoryId);

    List<com.pranay.code_review_platform_backend.entity.ClassMetadata> findByRepositoryId(Long repositoryId);
}