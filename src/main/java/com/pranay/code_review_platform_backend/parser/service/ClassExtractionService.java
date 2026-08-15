package com.pranay.code_review_platform_backend.parser.service;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.pranay.code_review_platform_backend.parser.model.ClassMetadata;
import com.pranay.code_review_platform_backend.repository.ClassMetadataRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ClassExtractionService {

    private final ClassMetadataRepository classMetadataRepository;

    public ClassExtractionService(
            ClassMetadataRepository classMetadataRepository) {
        this.classMetadataRepository = classMetadataRepository;
    }

    public void extractClasses(String repositoryPath, Long repositoryId)
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

            String packageName = compilationUnit
                    .getPackageDeclaration()
                    .map(packageDeclaration ->
                            packageDeclaration.getNameAsString())
                    .orElse("");

            List<ClassOrInterfaceDeclaration> classes =
                    compilationUnit.findAll(ClassOrInterfaceDeclaration.class);

            for (ClassOrInterfaceDeclaration classDeclaration : classes) {

                ClassMetadata metadata = ClassMetadata.builder()
                        .repositoryId(repositoryId)
                        .className(classDeclaration.getNameAsString())
                        .packageName(packageName)
                        .filePath(filePath.toString())
                        .isInterface(classDeclaration.isInterface())
                        .isAbstract(classDeclaration.isAbstract())
                        .build();

                classMetadataRepository.save(metadata);
            }

        } catch (Exception e) {

            System.err.println(
                    "Failed to parse: " + filePath
            );

            e.printStackTrace();
        }
    }
}
