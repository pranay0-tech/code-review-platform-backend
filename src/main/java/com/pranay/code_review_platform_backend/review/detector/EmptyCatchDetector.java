package main.java.com.pranay.code_review_platform_backend.review.detector;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.stmt.CatchClause;
import com.pranay.code_review_platform_backend.review.model.ReviewIssue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmptyCatchDetector {

    public List<ReviewIssue> detect(
            CompilationUnit compilationUnit,
            Long repositoryId,
            String fileName
    ) {

        List<ReviewIssue> issues = new ArrayList<>();

        for (CatchClause catchClause :
                compilationUnit.findAll(CatchClause.class)) {

            if (!catchClause.getBody().getStatements().isEmpty()) {
                continue;
            }

            String className = catchClause.findAncestor(
                    ClassOrInterfaceDeclaration.class
            )
            .map(ClassOrInterfaceDeclaration::getNameAsString)
            .orElse(null);

            ReviewIssue issue = ReviewIssue.builder()
                    .repositoryId(repositoryId)
                    .severity(ReviewIssue.Severity.HIGH)
                    .type(ReviewIssue.IssueType.EMPTY_CATCH)
                    .fileName(fileName)
                    .className(className)
                    .description(
                            "Empty catch block detected for exception '"
                                    + catchClause.getParameter().getNameAsString()
                                    + "'. The exception is silently ignored."
                    )
                    .suggestion(
                            "Handle the exception appropriately, log it, "
                                    + "or rethrow it if it cannot be handled here."
                    )
                    .build();

            issues.add(issue);
        }

        return issues;
    }
}