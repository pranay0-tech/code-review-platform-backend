package com.pranay.code_review_platform_backend.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

@Service
public class RepositoryFileService {

    public long countFiles(String repositoryPath)
            throws IOException {

        try (Stream<Path> paths =
                     Files.walk(Paths.get(repositoryPath))) {

            return paths
                    .filter(Files::isRegularFile)
                    .count();
        }
    }
}
