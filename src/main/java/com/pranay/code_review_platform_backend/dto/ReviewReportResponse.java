package main.java.com.pranay.code_review_platform_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewReportResponse {

    private int healthScore;

    private long criticalIssues;

    private long highIssues;

    private long mediumIssues;

    private long lowIssues;
}