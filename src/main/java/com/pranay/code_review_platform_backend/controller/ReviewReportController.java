package main.java.com.pranay.code_review_platform_backend.controller;

import com.pranay.code_review_platform_backend.review.dto.ReviewReportResponse;
import com.pranay.code_review_platform_backend.review.service.ReviewReportService;

import main.java.com.pranay.code_review_platform_backend.dto.AIRepositorySummaryResponse;
import main.java.com.pranay.code_review_platform_backend.review.model.ReviewIssue;
import main.java.com.pranay.code_review_platform_backend.service.AIRepositorySummaryService;
import com.pranay.code_review_platform_backend.review.model.ReviewIssue;
import com.pranay.code_review_platform_backend.review.repository.ReviewIssueRepository;
import com.pranay.code_review_platform_backend.review.service.AIRepositorySummaryService;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/repositories")
public class ReviewReportController {

    private final ReviewReportService reviewReportService;

    public ReviewReportController(
            ReviewReportService reviewReportService
    ) {
        this.reviewReportService = reviewReportService;
    }

    @GetMapping("/{id}/review-report")
    public ResponseEntity<ReviewReportResponse> getReviewReport(
            @PathVariable Long id
    ) {

        ReviewReportResponse report =
                reviewReportService.generateReport(id);

        return ResponseEntity.ok(report);
    }

    private final AIRepositorySummaryService aiRepositorySummaryService;

public ReviewReportController(
        ReviewReportService reviewReportService,
        AIRepositorySummaryService aiRepositorySummaryService
) {
    this.reviewReportService = reviewReportService;
    this.aiRepositorySummaryService = aiRepositorySummaryService;
}

@GetMapping("/{id}/ai-summary")
public ResponseEntity<AIRepositorySummaryResponse> getAISummary(
        @PathVariable Long id
) {

    List<ReviewIssue> issues =
            reviewIssueRepository.findByRepositoryId(id);

    String summary =
            aiRepositorySummaryService.generateSummary(issues);

    return ResponseEntity.ok(
            new AIRepositorySummaryResponse(summary)
    );
}
}