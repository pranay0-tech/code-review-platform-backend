package main.java.com.pranay.code_review_platform_backend.service;

import com.pranay.code_review_platform_backend.review.model.ReviewIssue;
import org.springframework.stereotype.Service;

@Service
public class ReviewSuggestionService {

    private final AIReviewService aiReviewService;

    public ReviewSuggestionService(AIReviewService aiReviewService) {
        this.aiReviewService = aiReviewService;
    }

    /**
     * Generates an actionable AI recommendation by scoping the LLM prompt 
     * directly to a detected ReviewIssue and its snippet.
     */
    public String generateSuggestion(String codeSnippet, ReviewIssue issue) {
        String prompt = """
                You are a senior software engineer conducting a code review.

                Review the following Java code snippet with context to a specific detected issue.

                Detected Issue Details:
                - Issue Type: %s
                - Severity: %s
                - Description: %s

                Task:
                1. Briefly explain why this snippet triggers the issue.
                2. Provide concrete, concise refactoring advice or a modernized code suggestion to fix it.

                Code Snippet:
                %s
                """.formatted(
                issue.getIssueType(),
                issue.getSeverity(),
                issue.getDescription(),
                codeSnippet
        );

        return aiReviewService.generateSuggestion(prompt);
    }
}