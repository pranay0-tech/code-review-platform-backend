package com.pranay.code_review_platform_backend.controller;

import com.pranay.code_review_platform_backend.dto.request.ConnectRepositoryRequest;
import com.pranay.code_review_platform_backend.entity.Repository;
import com.pranay.code_review_platform_backend.service.RepositoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/repositories")
public class RepositoryController {

    private final RepositoryService repositoryService;

    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @PostMapping("/connect")
    public ResponseEntity<Repository> connectRepository(
            @Valid @RequestBody ConnectRepositoryRequest request) {

        Repository repository = repositoryService.connectRepository(request);

        return ResponseEntity.ok(repository);
    }
}