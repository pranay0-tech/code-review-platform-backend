package main.java.com.pranay.code_review_platform_backend.review.detector;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.pranay.code_review_platform_backend.review.model.ReviewIssue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GodClassDetector {

    private static final int MAX_LINES = 500;
    private static final int MAX_METHODS = 20;

    public List<ReviewIssue> detect(
            CompilationUnit compilationUnit,
            Long repositoryId,
            String fileName
    ) {

        List<ReviewIssue> issues = new ArrayList<>();

        for (ClassOrInterfaceDeclaration classDeclaration :
                compilationUnit.findAll(ClassOrInterfaceDeclaration.class)) {

            if (classDeclaration.getRange().isEmpty()) {
                continue;
            }

            int startLine = classDeclaration.getRange().get().begin.line;
            int endLine = classDeclaration.getRange().get().end.line;

            int lineCount = endLine - startLine + 1;

            int methodCount =
                    classDeclaration.getMethods().size();

            boolean tooManyLines = lineCount > MAX_LINES;
            boolean tooManyMethods = methodCount > MAX_METHODS;

            if (tooManyLines || tooManyMethods) {

                StringBuilder description = new StringBuilder();

                description.append("Class '")
                        .append(classDeclaration.getNameAsString())
                        .append("' may be a God Class.");

                if (tooManyLines) {
                    description.append(" It contains ")
                            .append(lineCount)
                            .append(" lines.");
                }

                if (tooManyMethods) {
                    description.append(" It contains ")
                            .append(methodCount)
                            .append(" methods.");
                }

                ReviewIssue issue = ReviewIssue.builder()
                        .repositoryId(repositoryId)
                        .severity(ReviewIssue.Severity.HIGH)
                        .type(ReviewIssue.IssueType.GOD_CLASS)
                        .fileName(fileName)
                        .className(classDeclaration.getNameAsString())
                        .methodName(null)
                        .description(description.toString())
                        .suggestion(
                                "Consider splitting this class into smaller classes "
                                        + "with focused responsibilities."
                        )
                        .build();

                issues.add(issue);
            }
        }

        return issues;
    }
}