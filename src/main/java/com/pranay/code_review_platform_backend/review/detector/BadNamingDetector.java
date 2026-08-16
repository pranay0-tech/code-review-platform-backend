package main.java.com.pranay.code_review_platform_backend.review.detector;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.pranay.code_review_platform_backend.review.model.ReviewIssue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class BadNamingDetector {

    private static final Set<String> BAD_METHOD_NAMES = Set.of(
            "abc",
            "xyz",
            "foo",
            "bar",
            "test"
    );

    public List<ReviewIssue> detect(
            CompilationUnit compilationUnit,
            Long repositoryId,
            String fileName
    ) {

        List<ReviewIssue> issues = new ArrayList<>();

        // Check variables
        for (VariableDeclarator variable :
                compilationUnit.findAll(VariableDeclarator.class)) {

            String name = variable.getNameAsString();

            if (isBadName(name)) {

                String className = variable.findAncestor(
                        ClassOrInterfaceDeclaration.class
                )
                .map(ClassOrInterfaceDeclaration::getNameAsString)
                .orElse(null);

                issues.add(createIssue(
                        repositoryId,
                        fileName,
                        className,
                        "Variable",
                        name
                ));
            }
        }

        // Check methods
        for (MethodDeclaration method :
                compilationUnit.findAll(MethodDeclaration.class)) {

            String name = method.getNameAsString();

            if (isBadMethodName(name)) {

                String className = method.findAncestor(
                        ClassOrInterfaceDeclaration.class
                )
                .map(ClassOrInterfaceDeclaration::getNameAsString)
                .orElse(null);

                issues.add(createIssue(
                        repositoryId,
                        fileName,
                        className,
                        "Method",
                        name
                ));
            }
        }

        return issues;
    }

    private boolean isBadName(String name) {

        return name.length() < 2;
    }

    private boolean isBadMethodName(String name) {

        return name.length() < 3
                || BAD_METHOD_NAMES.contains(name);
    }

    private ReviewIssue createIssue(
            Long repositoryId,
            String fileName,
            String className,
            String elementType,
            String name
    ) {

        return ReviewIssue.builder()
                .repositoryId(repositoryId)
                .severity(ReviewIssue.Severity.LOW)
                .type(ReviewIssue.IssueType.BAD_NAMING)
                .fileName(fileName)
                .className(className)
                .description(
                        elementType + " name '" + name
                                + "' is not meaningful or descriptive."
                )
                .suggestion(
                        "Use a descriptive name that clearly communicates "
                                + "the purpose of this " + elementType.toLowerCase() + "."
                )
                .build();
    }
}