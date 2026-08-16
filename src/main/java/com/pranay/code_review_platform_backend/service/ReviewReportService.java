package main.java.com.pranay.code_review_platform_backend.service;

import com.pranay.code_review_platform_backend.review.dto.ReviewReportResponse;
import com.pranay.code_review_platform_backend.review.model.ReviewIssue;
import com.pranay.code_review_platform_backend.review.repository.ReviewIssueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewReportService {

    private final ReviewIssueRepository reviewIssueRepository;
    private final HealthScoreCalculator healthScoreCalculator;

    public ReviewReportService(
            ReviewIssueRepository reviewIssueRepository,
            HealthScoreCalculator healthScoreCalculator
    ) {
        this.reviewIssueRepository = reviewIssueRepository;
        this.healthScoreCalculator = healthScoreCalculator;
    }

    public ReviewReportResponse generateReport(Long repositoryId) {

        List<ReviewIssue> issues =
                reviewIssueRepository.findByRepositoryId(repositoryId);

        int healthScore =
                healthScoreCalculator.calculate(issues);

        long criticalIssues = issues.stream()
                .filter(issue ->
                        issue.getSeverity() == ReviewIssue.Severity.CRITICAL)
                .count();

        long highIssues = issues.stream()
                .filter(issue ->
                        issue.getSeverity() == ReviewIssue.Severity.HIGH)
                .count();

        long mediumIssues = issues.stream()
                .filter(issue ->
                        issue.getSeverity() == ReviewIssue.Severity.MEDIUM)
                .count();

        long lowIssues = issues.stream()
                .filter(issue ->
                        issue.getSeverity() == ReviewIssue.Severity.LOW)
                .count();

        return ReviewReportResponse.builder()
                .healthScore(healthScore)
                .criticalIssues(criticalIssues)
                .highIssues(highIssues)
                .mediumIssues(mediumIssues)
                .lowIssues(lowIssues)
                .build();
    }
}