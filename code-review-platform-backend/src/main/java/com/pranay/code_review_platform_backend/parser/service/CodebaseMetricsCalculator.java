package com.pranay.code_review_platform_backend.parser.service;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.pranay.code_review_platform_backend.parser.dto.RepositoryMetricsDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodebaseMetricsCalculator {

    /**
     * Calculates repository statistics from JavaParser Compilation Units.
     * 
     * @param parsedUnits List of AST Compilation Units generated during parsing
     * @return Calculated real codebase metrics
     */
    public RepositoryMetricsDto calculateMetricsFromAst(List<CompilationUnit> parsedUnits) {
        long controllers = 0;
        long services = 0;
        long repositories = 0;
        long entities = 0;
        long totalClasses = 0;
        long totalMethods = 0;

        for (CompilationUnit cu : parsedUnits) {
            List<ClassOrInterfaceDeclaration> classes = cu.findAll(ClassOrInterfaceDeclaration.class);

            for (ClassOrInterfaceDeclaration type : classes) {
                totalClasses++;
                totalMethods += type.getMethods().size();

                // 1. Check annotations
                boolean isController = hasAnnotation(type, "RestController") || hasAnnotation(type, "Controller");
                boolean isService = hasAnnotation(type, "Service");
                boolean isRepository = hasAnnotation(type, "Repository") || isSpringDataRepository(type);
                boolean isEntity = hasAnnotation(type, "Entity");

                if (isController) controllers++;
                if (isService) services++;
                if (isRepository) repositories++;
                if (isEntity) entities++;
            }
        }

        // 2. Compute Average Methods Per Class: totalMethods / totalClasses
        double averageMethods = (totalClasses > 0) 
                ? Math.round(((double) totalMethods / totalClasses) * 10.0) / 10.0 
                : 0.0;

        return RepositoryMetricsDto.builder()
                .controllers(controllers)
                .services(services)
                .repositories(repositories)
                .entities(entities)
                .totalClasses(totalClasses)
                .totalMethods(totalMethods)
                .averageMethodsPerClass(averageMethods)
                .build();
    }

    private boolean hasAnnotation(ClassOrInterfaceDeclaration type, String annotationName) {
        return type.getAnnotations().stream()
                .anyMatch(a -> a.getNameAsString().equals(annotationName));
    }

    private boolean isSpringDataRepository(ClassOrInterfaceDeclaration type) {
        return type.getExtendedTypes().stream()
                .anyMatch(extended -> extended.getNameAsString().contains("Repository") ||
                                      extended.getNameAsString().contains("JpaRepository") ||
                                      extended.getNameAsString().contains("CrudRepository"));
    }
}