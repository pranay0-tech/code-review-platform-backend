package com.pranay.code_review_platform_backend.parser.service;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.pranay.code_review_platform_backend.*;
import com.pranay.code_review_platform_backend.parser.model.ClassDependency;
import com.pranay.code_review_platform_backend.repository.ClassDependencyRepository;
import com.pranay.code_review_platform_backend.repository.ClassDependencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DependencyExtractorService {

    private final ClassDependencyRepository dependencyRepository;

    // Filter out primitive types and standard Java wrapper/utility classes
    private static final Set<String> IGNORED_TYPES = Set.of(
            "String", "Integer", "Long", "Double", "Boolean", "Float",
            "Object", "List", "Set", "Map", "Optional", "int", "long", "boolean", "double"
    );

    @Transactional
    public List<ClassDependency> extractAndSaveDependencies(CompilationUnit cu) {
        List<ClassDependency> savedDependencies = new ArrayList<>();

        // Find main class or interface declaration
        Optional<ClassOrInterfaceDeclaration> mainClassOpt = cu.findFirst(ClassOrInterfaceDeclaration.class);
        if (mainClassOpt.isEmpty()) {
            return savedDependencies;
        }

        String sourceClass = mainClassOpt.get().getNameAsString();
        Set<String> targetClasses = new HashSet<>();

        // 1. Extract from Fields
        cu.findAll(FieldDeclaration.class).forEach(field -> {
            field.getVariables().forEach(var -> {
                String typeName = var.getType().asString();
                cleanAndAddTarget(typeName, targetClasses, sourceClass);
            });
        });

        // 2. Extract from Method Parameters
        cu.findAll(Parameter.class).forEach(param -> {
            String typeName = param.getType().asString();
            cleanAndAddTarget(typeName, targetClasses, sourceClass);
        });

        // 3. Extract from Object Instantiations (new TargetClass())
        cu.findAll(ObjectCreationExpr.class).forEach(objExpr -> {
            String typeName = objExpr.getType().asString();
            cleanAndAddTarget(typeName, targetClasses, sourceClass);
        });

        // 4. Save unique dependencies to PostgreSQL
        for (String targetClass : targetClasses) {
            if (!dependencyRepository.existsBySourceClassAndTargetClass(sourceClass, targetClass)) {
                ClassDependency dependency = ClassDependency.builder()
                        .sourceClass(sourceClass)
                        .targetClass(targetClass)
                        .build();
                savedDependencies.add(dependencyRepository.save(dependency));
            }
        }

        return savedDependencies;
    }

    private void cleanAndAddTarget(String rawType, Set<String> targets, String sourceClass) {
        // Strip generics if present (e.g., List<UserService> -> UserService)
        String cleaned = rawType.replaceAll("<.*>", "").trim();

        if (!cleaned.isEmpty() 
                && !cleaned.equals(sourceClass) 
                && !IGNORED_TYPES.contains(cleaned)) {
            targets.add(cleaned);
        }
    }
}
