package com.pranay.code_review_platform_backend.parser.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public class RepositoryParserService {

    public ScanResult scanRepository(String repositoryPath) throws IOException {

        Path rootPath = Path.of(repositoryPath);

        if (!Files.exists(rootPath)) {
            throw new IllegalArgumentException(
                    "Repository does not exist: " + repositoryPath
            );
        }

        if (!Files.isDirectory(rootPath)) {
            throw new IllegalArgumentException(
                    "Path is not a directory: " + repositoryPath
            );
        }

        long totalFiles;
        long javaFiles;

        try (Stream<Path> paths = Files.walk(rootPath)) {

            totalFiles = paths
                    .filter(Files::isRegularFile)
                    .count();
        }

        try (Stream<Path> paths = Files.walk(rootPath)) {

            javaFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(path ->
                            path.toString().endsWith(".java"))
                    .count();
        }

        return new ScanResult(totalFiles, javaFiles);
    }

    public record ScanResult(
            long totalFiles,
            long javaFiles
    ) {
    }
}
