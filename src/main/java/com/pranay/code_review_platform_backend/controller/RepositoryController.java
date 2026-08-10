package com.pranay.code_review_platform_backend.controller;

import com.pranay.code_review_platform_backend.dto.request.ConnectRepositoryRequest;
import com.pranay.code_review_platform_backend.dto.response.CloneRepositoryResponse;
import com.pranay.code_review_platform_backend.entity.Repository;
import com.pranay.code_review_platform_backend.parser.service.RepositoryParserService;
import com.pranay.code_review_platform_backend.service.RepositoryService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException; 

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

    @PostMapping("/clone/{id}")
    public ResponseEntity<CloneRepositoryResponse> cloneRepository(@PathVariable Long id) throws Exception {

        // Service returns CloneRepositoryResponse directly
        CloneRepositoryResponse response = repositoryService.cloneRepository(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/scan")
    public ResponseEntity<RepositoryParserService.ScanResult> scanRepository(@PathVariable Long id) throws IOException {
        RepositoryParserService.ScanResult result = repositoryService.scanRepository(id);
        return ResponseEntity.ok(result);
    }
}