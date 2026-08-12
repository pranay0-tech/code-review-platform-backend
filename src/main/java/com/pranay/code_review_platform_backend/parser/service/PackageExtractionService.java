package com.pranay.code_review_platform_backend.parser.service;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.pranay.code_review_platform_backend.parser.model.PackageMetadata;
import com.pranay.code_review_platform_backend.repository.PackageMetadataRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

@Service
public class PackageExtractionService {

    private final PackageMetadataRepository packageMetadataRepository;

    public PackageExtractionService(
            PackageMetadataRepository packageMetadataRepository) {
        this.packageMetadataRepository = packageMetadataRepository;
    }

    public void extractPackages(
            String repositoryPath,
            Long repositoryId) throws IOException {

        Path rootPath = Paths.get(repositoryPath);

        try (Stream<Path> paths = Files.walk(rootPath)) {

            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path ->
                            extractFromFile(path, repositoryId));
        }
    }

    private void extractFromFile(
            Path filePath,
            Long repositoryId) {

        try {

            CompilationUnit compilationUnit =
                    StaticJavaParser.parse(filePath);

            compilationUnit
                    .getPackageDeclaration()
                    .ifPresent(packageDeclaration -> {

                        String packageName =
                                packageDeclaration.getNameAsString();

                        // Check if this package already exists for this repository
                        boolean exists = packageMetadataRepository
                                .findByRepositoryIdAndPackageName(
                                        repositoryId,
                                        packageName
                                )
                                .isPresent();

                        // Save only if it does not exist yet
                        if (!exists) {
                            PackageMetadata metadata =
                                    PackageMetadata.builder()
                                            .repositoryId(repositoryId)
                                            .packageName(packageName)
                                            .build();

                            packageMetadataRepository.save(metadata);
                        }
                    });

        } catch (Exception e) {

            System.err.println(
                    "Failed to extract package from: "
                            + filePath
            );

            e.printStackTrace();
        }
    }
}