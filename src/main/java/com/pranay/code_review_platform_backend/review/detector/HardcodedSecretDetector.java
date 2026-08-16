package main.java.com.pranay.code_review_platform_backend.review.detector;

import com.pranay.code_review_platform_backend.review.model.ReviewIssue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class HardcodedSecretDetector {

    // Sensitive variable name patterns (e.g., password, apiKey, secret, token)
    private static final Pattern SECRET_KEYWORD_PATTERN = Pattern.compile(
        "(?i).*(password|secret|api[_-]?key|jwt|auth[_-]?token|private[_-]?key).*"
    );

    public List<ReviewIssue> analyzeLine(Long repositoryId, String filePath, int lineNumber, String variableName, String assignedValue) {
        List<ReviewIssue> issues = new ArrayList<>();

        if (SECRET_KEYWORD_PATTERN.matcher(variableName).matches() && isLiteralString(assignedValue)) {
            ReviewIssue issue = new ReviewIssue(
                repositoryId,
                "HARDCODED_SECRET",
                String.format("Potential hardcoded secret detected in variable '%s'.", variableName),
                filePath,
                lineNumber,
                "CRITICAL",
                "Move sensitive credentials out of source code into environment variables or a secret management service (e.g., Vault, AWS Secrets Manager)."
            );
            issues.add(issue);
        }

        return issues;
    }

    private boolean isLiteralString(String value) {
        return value != null && value.startsWith("\"") && value.endsWith("\"") && value.length() > 2;
    }
}