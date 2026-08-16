package main.java.com.pranay.code_review_platform_backend.review.detector;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.LongLiteralExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.FloatingPointLiteralExpr;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.pranay.code_review_platform_backend.review.model.ReviewIssue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MagicNumberDetector {

    public List<ReviewIssue> detect(
            CompilationUnit compilationUnit,
            Long repositoryId,
            String fileName
    ) {

        List<ReviewIssue> issues = new ArrayList<>();

        compilationUnit.findAll(IntegerLiteralExpr.class)
                .forEach(number -> checkNumber(
                        number.getValue(),
                        number.getParentNode().orElse(null),
                        compilationUnit,
                        repositoryId,
                        fileName,
                        issues
                ));

        compilationUnit.findAll(LongLiteralExpr.class)
                .forEach(number -> checkNumber(
                        number.getValue(),
                        number.getParentNode().orElse(null),
                        compilationUnit,
                        repositoryId,
                        fileName,
                        issues
                ));

        compilationUnit.findAll(DoubleLiteralExpr.class)
                .forEach(number -> checkNumber(
                        number.getValue(),
                        number.getParentNode().orElse(null),
                        compilationUnit,
                        repositoryId,
                        fileName,
                        issues
                ));

        compilationUnit.findAll(FloatingPointLiteralExpr.class)
                .forEach(number -> checkNumber(
                        number.getValue(),
                        number.getParentNode().orElse(null),
                        compilationUnit,
                        repositoryId,
                        fileName,
                        issues
                ));

        return issues;
    }

    private void checkNumber(
            String value,
            com.github.javaparser.ast.Node parent,
            CompilationUnit compilationUnit,
            Long repositoryId,
            String fileName,
            List<ReviewIssue> issues
    ) {

        if (isAllowedValue(value)) {
            return;
        }

        String className = parent == null
                ? null
                : parent.findAncestor(
                        ClassOrInterfaceDeclaration.class
                )
                .map(ClassOrInterfaceDeclaration::getNameAsString)
                .orElse(null);

        ReviewIssue issue = ReviewIssue.builder()
                .repositoryId(repositoryId)
                .severity(ReviewIssue.Severity.LOW)
                .type(ReviewIssue.IssueType.MAGIC_NUMBER)
                .fileName(fileName)
                .className(className)
                .description(
                        "Magic number '" + value
                                + "' is used directly in the code."
                )
                .suggestion(
                        "Replace the value with a named constant "
                                + "that explains its purpose."
                )
                .build();

        issues.add(issue);
    }

    private boolean isAllowedValue(String value) {

        String normalized = value
                .replace("L", "")
                .replace("l", "")
                .replace("F", "")
                .replace("f", "")
                .replace("D", "")
                .replace("d", "");

        return normalized.equals("0")
                || normalized.equals("1")
                || normalized.equals("-1");
    }
}