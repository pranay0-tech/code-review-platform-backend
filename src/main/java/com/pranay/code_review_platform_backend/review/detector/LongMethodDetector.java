package main.java.com.pranay.code_review_platform_backend.review.detector;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.CompilationUnit;
import com.pranay.code_review_platform_backend.review.model.ReviewIssue;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class LongMethodDetector {

    private static final int MAX_LINES = 50;

    public List<ReviewIssue> detect(
            CompilationUnit compilationUnit,
            Long repositoryId,
            String fileName
    ) {

        List<ReviewIssue> issues = new ArrayList<>();

        for (MethodDeclaration method : compilationUnit.findAll(MethodDeclaration.class)) {

            if (method.getRange().isEmpty()) {
                continue;
            }

            int startLine = method.getRange().get().begin.line;
            int endLine = method.getRange().get().end.line;

            int lineCount = endLine - startLine + 1;

            if (lineCount > MAX_LINES) {

                String className = method.findAncestor(
                        com.github.javaparser.ast.body.ClassOrInterfaceDeclaration.class
                )
                .map(classDeclaration ->
                        classDeclaration.getNameAsString()
                )
                .orElse(null);

                ReviewIssue issue = ReviewIssue.builder()
                        .repositoryId(repositoryId)
                        .severity(ReviewIssue.Severity.HIGH)
                        .type(ReviewIssue.IssueType.LONG_METHOD)
                        .fileName(fileName)
                        .className(className)
                        .methodName(method.getNameAsString())
                        .description(
                                "Method '" + method.getNameAsString()
                                        + "' contains " + lineCount
                                        + " lines, exceeding the maximum allowed length of "
                                        + MAX_LINES + " lines."
                        )
                        .suggestion(
                                "Consider breaking this method into smaller methods "
                                        + "with clear responsibilities."
                        )
                        .createdAt(LocalDateTime.now())
                        .build();

                issues.add(issue);
            }
        }

        return issues;
    }
}