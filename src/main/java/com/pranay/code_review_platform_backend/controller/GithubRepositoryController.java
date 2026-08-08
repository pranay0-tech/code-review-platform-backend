package com.pranay.code_review_platform_backend.controller;

import com.pranay.code_review_platform_backend.dto.response.GithubRepositoryResponse;
import com.pranay.code_review_platform_backend.service.GithubRepositoryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GithubRepositoryController {

    private final GithubRepositoryService githubRepositoryService;

    public GithubRepositoryController(
            GithubRepositoryService githubRepositoryService) {
        this.githubRepositoryService = githubRepositoryService;
    }

    @GetMapping("/repos")
    public ResponseEntity<List<GithubRepositoryResponse>> getRepositories(
            @RequestParam Long githubUserId) {

        return ResponseEntity.ok(
                githubRepositoryService.getRepositories(githubUserId)
        );
    }
}
