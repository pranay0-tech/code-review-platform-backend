package com.pranay.code_review_platform_backend.controller;

import com.pranay.code_review_platform_backend.parser.dto.RepositorySummaryResponse;
import com.pranay.code_review_platform_backend.service.RepositorySummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/repositories")
public class RepositorySummaryController {

    private final RepositorySummaryService repositorySummaryService;

    public RepositorySummaryController(
            RepositorySummaryService repositorySummaryService) {

        this.repositorySummaryService =
                repositorySummaryService;
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<RepositorySummaryResponse> getSummary(
            @PathVariable Long id) throws IOException {

        return ResponseEntity.ok(
                repositorySummaryService.getSummary(id)
        );
    }
}