package com.pranay.code_review_platform_backend.repository;

import com.pranay.code_review_platform_backend.entity.ApiEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiEndpointRepository extends JpaRepository<ApiEndpoint, Long> {

    // Fetch all endpoints for a specific repository (used for "Show all APIs")
    List<ApiEndpoint> findByRepositoryId(Long repositoryId);

    // Fetch endpoints by repository and HTTP method (e.g., all POST routes)
    List<ApiEndpoint> findByRepositoryIdAndHttpMethod(Long repositoryId, String httpMethod);

    // Wipe old endpoints prior to re-indexing a repository
    void deleteByRepositoryId(Long repositoryId);
}