package main.java.com.pranay.code_review_platform_backend.service;

import com.pranay.code_review_platform_backend.review.model.ReviewIssue;
import com.pranay.code_review_platform_backend.review.repository.ReviewIssueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AIRepositorySummaryService {

    private final ReviewIssueRepository reviewIssueRepository;
    private final AIReviewService aiReviewService;

    public AIRepositorySummaryService(
            ReviewIssueRepository reviewIssueRepository,
            AIReviewService aiReviewService
    ) {
        this.reviewIssueRepository = reviewIssueRepository;
        this.aiReviewService = aiReviewService;
    }

    public String generateSummary(Long repositoryId) {

        List<ReviewIssue> issues =
                reviewIssueRepository.findByRepositoryId(repositoryId);

        String issueSummary = issues.stream()
                .map(issue -> String.format(
                        "Type: %s | Severity: %s | File: %s | Class: %s | Method: %s | Description: %s",
                        issue.getType(),
                        issue.getSeverity(),
                        issue.getFileName(),
                        issue.getClassName(),
                        issue.getMethodName(),
                        issue.getDescription()
                ))
                .collect(Collectors.joining("\n"));

        String prompt = """
                You are a senior software architect.

                Summarize the quality of this repository.

                Mention:

                1. Strengths
                2. Weaknesses
                3. Refactoring opportunities
                4. Risk areas

                Keep the summary concise and practical.

                Repository review findings:

                %s
                """.formatted(issueSummary);

        return aiReviewService.generateSuggestion(prompt);
    }
}