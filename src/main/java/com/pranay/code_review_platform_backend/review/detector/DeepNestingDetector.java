package main.java.com.pranay.code_review_platform_backend.review.detector;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.*;
import com.pranay.code_review_platform_backend.review.model.ReviewIssue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeepNestingDetector {

    private static final int MAX_DEPTH = 3;

    public List<ReviewIssue> detect(
            CompilationUnit compilationUnit,
            Long repositoryId,
            String fileName
    ) {

        List<ReviewIssue> issues = new ArrayList<>();

        for (MethodDeclaration method :
                compilationUnit.findAll(MethodDeclaration.class)) {

            int maxDepth = calculateMaxDepth(method);

            if (maxDepth > MAX_DEPTH) {

                String className = method.findAncestor(
                        com.github.javaparser.ast.body.ClassOrInterfaceDeclaration.class
                )
                .map(classDeclaration ->
                        classDeclaration.getNameAsString()
                )
                .orElse(null);

                ReviewIssue issue = ReviewIssue.builder()
                        .repositoryId(repositoryId)
                        .severity(ReviewIssue.Severity.MEDIUM)
                        .type(ReviewIssue.IssueType.DEEP_NESTING)
                        .fileName(fileName)
                        .className(className)
                        .methodName(method.getNameAsString())
                        .description(
                                "Method '" + method.getNameAsString()
                                        + "' has a maximum nesting depth of "
                                        + maxDepth
                                        + ", exceeding the allowed depth of "
                                        + MAX_DEPTH + "."
                        )
                        .suggestion(
                                "Reduce nesting by using early returns, "
                                        + "extracting methods, or simplifying "
                                        + "conditional logic."
                        )
                        .build();

                issues.add(issue);
            }
        }

        return issues;
    }

    private int calculateMaxDepth(Node node) {

        return calculateDepth(node, 0);
    }

    private int calculateDepth(Node node, int currentDepth) {

        int depth = currentDepth;

        for (Node child : node.getChildNodes()) {

            int childDepth = currentDepth;

            if (isNestingStatement(child)) {
                childDepth++;
            }

            depth = Math.max(
                    depth,
                    calculateDepth(child, childDepth)
            );
        }

        return depth;
    }

    private boolean isNestingStatement(Node node) {

        return node instanceof IfStmt
                || node instanceof ForStmt
                || node instanceof ForEachStmt
                || node instanceof WhileStmt
                || node instanceof DoStmt
                || node instanceof SwitchStmt
                || node instanceof TryStmt;
    }
}