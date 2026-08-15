package com.pranay.code_review_platform_backend.parser.service;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.pranay.code_review_platform_backend.parser.model.ImportMetadata;
import com.pranay.code_review_platform_backend.repository.ImportMetadataRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

@Service
public class ImportExtractionService {

    private final ImportMetadataRepository importMetadataRepository;

    public ImportExtractionService(
            ImportMetadataRepository importMetadataRepository) {
        this.importMetadataRepository = importMetadataRepository;
    }

    public void extractImports(String repositoryPath, Long repositoryId)
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

            String className = compilationUnit
                    .findFirst(ClassOrInterfaceDeclaration.class)
                    .map(ClassOrInterfaceDeclaration::getNameAsString)
                    .orElse(filePath.getFileName().toString()
                            .replace(".java", ""));

            for (ImportDeclaration importDeclaration :
                    compilationUnit.getImports()) {

                ImportMetadata metadata = ImportMetadata.builder()
                        .repositoryId(repositoryId)
                        .className(className)
                        .importName(importDeclaration.getNameAsString())
                        .build();

                importMetadataRepository.save(metadata);
            }

        } catch (Exception e) {

            System.err.println(
                    "Failed to parse imports: " + filePath
            );

            e.printStackTrace();
        }
    }
}