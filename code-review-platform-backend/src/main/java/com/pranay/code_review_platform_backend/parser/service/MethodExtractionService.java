package com.pranay.code_review_platform_backend.parser.service;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.pranay.code_review_platform_backend.parser.model.MethodMetadata;
import com.pranay.code_review_platform_backend.repository.MethodMetadataRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

@Service
public class MethodExtractionService {

    private final MethodMetadataRepository methodMetadataRepository;

    public MethodExtractionService(
            MethodMetadataRepository methodMetadataRepository) {
        this.methodMetadataRepository = methodMetadataRepository;
    }

    public void extractMethods(String repositoryPath, Long repositoryId)
            throws IOException {

        Path rootPath = Paths.get(repositoryPath);

        try (Stream<Path> paths = Files.walk(rootPath)) {

            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> extractFromFile(path, repositoryId));
        }
    }

    private void extractFromFile(Path filePath, Long repositoryId) {

        try {

            CompilationUnit compilationUnit =
                    StaticJavaParser.parse(filePath);

            for (ClassOrInterfaceDeclaration classDeclaration :
                    compilationUnit.findAll(ClassOrInterfaceDeclaration.class)) {

                String className =
                        classDeclaration.getNameAsString();

                for (MethodDeclaration method :
                        classDeclaration.getMethods()) {

                    String accessModifier =
                            getAccessModifier(method);

                    MethodMetadata metadata = MethodMetadata.builder()
                            .repositoryId(repositoryId)
                            .methodName(method.getNameAsString())
                            .returnType(method.getType().asString())
                            .parameterCount(method.getParameters().size())
                            .className(className)
                            .accessModifier(accessModifier)
                            .build();

                    methodMetadataRepository.save(metadata);
                }
            }

        } catch (Exception e) {

            System.err.println(
                    "Failed to parse: " + filePath
            );

            e.printStackTrace();
        }
    }

    private String getAccessModifier(MethodDeclaration method) {

        if (method.isPublic()) {
            return "public";
        }

        if (method.isPrivate()) {
            return "private";
        }

        if (method.isProtected()) {
            return "protected";
        }

        return "package-private";
    }
}
