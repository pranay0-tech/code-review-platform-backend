package com.pranay.code_review_platform_backend.controller;

import com.pranay.code_review_platform_backend.parser.dto.ProjectOverviewResponse;
import com.pranay.code_review_platform_backend.parser.service.ProjectSummarizer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/repositories")
public class RepositoryOverviewController {

    private final ProjectSummarizer projectSummarizer;

    public RepositoryOverviewController(
            ProjectSummarizer projectSummarizer
    ) {
        this.projectSummarizer = projectSummarizer;
    }

    @GetMapping("/{id}/overview")
    public ProjectOverviewResponse getOverview(
            @PathVariable Long id
    ) {

        // Repository metadata retrieval will be connected here.

        return projectSummarizer.generateOverview(
                "Class metadata",
                "Package metadata",
                "Technology metadata"
        );
    }
}