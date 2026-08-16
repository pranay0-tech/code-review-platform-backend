package main.java.com.pranay.code_review_platform_backend.review.detector;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.pranay.code_review_platform_backend.review.model.ReviewIssue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UnusedImportDetector {

    public List<ReviewIssue> detect(
            CompilationUnit compilationUnit,
            Long repositoryId,
            String fileName
    ) {

        List<ReviewIssue> issues = new ArrayList<>();

        for (ImportDeclaration importDeclaration :
                compilationUnit.getImports()) {

            if (importDeclaration.isAsterisk()) {
                continue;
            }

            String importedName = importDeclaration.getNameAsString();

            String simpleName = importedName.substring(
                    importedName.lastIndexOf('.') + 1
            );

            boolean used = isImportUsed(
                    compilationUnit,
                    simpleName
            );

            if (!used) {

                ReviewIssue issue = ReviewIssue.builder()
                        .repositoryId(repositoryId)
                        .severity(ReviewIssue.Severity.LOW)
                        .type(ReviewIssue.IssueType.UNUSED_IMPORT)
                        .fileName(fileName)
                        .description(
                                "Import '" + importedName
                                        + "' is not used in this file."
                        )
                        .suggestion(
                                "Remove the unused import."
                        )
                        .build();

                issues.add(issue);
            }
        }

        return issues;
    }

    private boolean isImportUsed(
            CompilationUnit compilationUnit,
            String simpleName
    ) {

        // Check usages as a type.
        boolean usedAsType = compilationUnit
                .findAll(ClassOrInterfaceType.class)
                .stream()
                .anyMatch(type ->
                        type.getNameAsString().equals(simpleName)
                );

        if (usedAsType) {
            return true;
        }

        // Check other AST nodes for the identifier.
        return compilationUnit
                .findAll(Node.class)
                .stream()
                .anyMatch(node ->
                        node.getChildNodes().isEmpty()
                                && node.toString().equals(simpleName)
                );
    }
}