package com.pranay.code_review_platform_backend.controller;

import com.pranay.code_review_platform_backend.entity.ApiEndpoint;
import com.pranay.code_review_platform_backend.parser.dto.ApiEndpointSummaryResponse;
import com.pranay.code_review_platform_backend.repository.ApiEndpointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repositories")
@RequiredArgsConstructor
public class ApiMetadataController {

    private final ApiEndpointRepository apiEndpointRepository;

    @GetMapping("/{repositoryId}/apis")
    public ResponseEntity<List<ApiEndpointSummaryResponse>> getRepositoryApis(@PathVariable Long repositoryId) {
        List<ApiEndpoint> endpoints = apiEndpointRepository.findByRepositoryId(repositoryId);

        List<ApiEndpointSummaryResponse> response = endpoints.stream()
                .map(ep -> ApiEndpointSummaryResponse.builder()
                        .method(ep.getHttpMethod())
                        .path(ep.getFullPath())
                        .build())
                .toList();

        return ResponseEntity.ok(response);
    }
}