package main.java.com.pranay.code_review_platform_backend.controller;

import com.pranay.code_review_platform_backend.review.dto.ReviewReportResponse;
import com.pranay.code_review_platform_backend.review.service.ReviewReportService;
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
}