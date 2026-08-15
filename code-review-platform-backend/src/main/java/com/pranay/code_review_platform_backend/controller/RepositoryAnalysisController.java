package com.pranay.code_review_platform_backend.controller;

import com.pranay.code_review_platform_backend.entity.Repository;
import com.pranay.code_review_platform_backend.parser.dto.StartParsingResponse;
import com.pranay.code_review_platform_backend.parser.model.ParsingJob;
import com.pranay.code_review_platform_backend.parser.model.ParsingStatus;
import com.pranay.code_review_platform_backend.repository.ParsingJobRepository;
import com.pranay.code_review_platform_backend.service.RepositoryParsingService;
import com.pranay.code_review_platform_backend.service.RepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/repositories")
@RequiredArgsConstructor
public class RepositoryAnalysisController {

    private final RepositoryService repositoryService;
    private final ParsingJobRepository parsingJobRepository;
    private final RepositoryParsingService repositoryParsingService;

    @PostMapping("/{id}/parse")
    public ResponseEntity<StartParsingResponse> startParsing(@PathVariable Long id) {

        Repository repository = (Repository) repositoryService.getRepositoryById(id);

        ParsingJob job = ParsingJob.builder()
                .repositoryId(id)
                .status(ParsingStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        job = parsingJobRepository.save(job);

        repositoryParsingService.parseRepository(
                job.getId(),
                repository.getLocalPath(),
                id
        );

        StartParsingResponse response = StartParsingResponse.builder()
                .repositoryId(id)
                .jobId(job.getId())
                .status(job.getStatus())
                .build();

        return ResponseEntity.accepted().body(response);
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<ParsingJob> getStatus(@PathVariable Long id) {

        ParsingJob job = parsingJobRepository
                .findTopByRepositoryIdOrderByCreatedAtDesc(id)
                .orElseThrow(() -> new RuntimeException("No parsing job found for repository id: " + id));

        return ResponseEntity.ok(job);
    }
}