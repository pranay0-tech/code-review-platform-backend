package main.java.com.pranay.code_review_platform_backend.review.detector;

import com.pranay.code_review_platform_backend.review.model.ReviewIssue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DuplicateCodeDetector {

    /**
     * Converts PMD CPD duplication match data into standardized ReviewIssue domain objects.
     * 
     * @param repositoryId Target repository ID
     * @param fileName     Path of the file containing duplication
     * @param lineNumber   Line where duplicate block starts
     * @param duplicatedLines Number of duplicated lines detected
     * @return List of normalized ReviewIssue entities
     */
    public List<ReviewIssue> adaptCpdResults(Long repositoryId, String fileName, Integer lineNumber, int duplicatedLines) {
        List<ReviewIssue> issues = new ArrayList<>();

        String description = String.format(
            "Found %d duplicated lines of code starting at line %d.", 
            duplicatedLines, 
            lineNumber
        );

        String suggestion = "Extract the duplicated logic into a shared method or reusable component.";

        ReviewIssue issue = new ReviewIssue(
            repositoryId,
            "DUPLICATE_CODE",
            description,
            fileName,
            lineNumber,
            "MEDIUM",
            suggestion
        );

        issues.add(issue);
        return issues;
    }
}