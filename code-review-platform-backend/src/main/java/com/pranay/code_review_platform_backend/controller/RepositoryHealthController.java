package com.pranay.code_review_platform_backend.controller;

import com.pranay.code_review_platform_backend.parser.dto.RepositoryHealthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/repositories")
@RequiredArgsConstructor
public class RepositoryHealthController {

    private final RepositoryHealthService repositoryHealthService;

    @GetMapping("/{id}/health")
    public RepositoryHealthResponse getHealthInsights(
            @PathVariable Long id
    ) {

        return repositoryHealthService
                .generateHealthInsights(id);
    }
}