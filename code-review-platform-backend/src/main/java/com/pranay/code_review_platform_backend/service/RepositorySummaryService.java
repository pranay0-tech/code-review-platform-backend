package com.pranay.code_review_platform_backend.service;

import com.pranay.code_review_platform_backend.parser.dto.RepositorySummaryResponse;
import com.pranay.code_review_platform_backend.parser.model.TechnologyMetadata;
import com.pranay.code_review_platform_backend.repository.ClassMetadataRepository;
import com.pranay.code_review_platform_backend.repository.MethodMetadataRepository;
import com.pranay.code_review_platform_backend.repository.PackageMetadataRepository;
import com.pranay.code_review_platform_backend.repository.RepositoryRepository;
import com.pranay.code_review_platform_backend.repository.TechnologyMetadataRepository;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class RepositorySummaryService {

    private final RepositoryRepository repositoryRepository;
    private final ClassMetadataRepository classMetadataRepository;
    private final MethodMetadataRepository methodMetadataRepository;
    private final PackageMetadataRepository packageMetadataRepository;
    private final TechnologyMetadataRepository technologyMetadataRepository;
    private final RepositoryFileService repositoryFileService;

    public RepositorySummaryService(
            RepositoryRepository repositoryRepository,
            ClassMetadataRepository classMetadataRepository,
            MethodMetadataRepository methodMetadataRepository,
            PackageMetadataRepository packageMetadataRepository,
            TechnologyMetadataRepository technologyMetadataRepository,
            RepositoryFileService repositoryFileService) {

        this.repositoryRepository = repositoryRepository;
        this.classMetadataRepository = classMetadataRepository;
        this.methodMetadataRepository = methodMetadataRepository;
        this.packageMetadataRepository = packageMetadataRepository;
        this.technologyMetadataRepository = technologyMetadataRepository;
        this.repositoryFileService = repositoryFileService;
    }

    public RepositorySummaryResponse getSummary(
            Long repositoryId) throws IOException {

        com.pranay.code_review_platform_backend.entity.Repository repository =
                repositoryRepository.findById(repositoryId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Repository not found"
                                )
                        );

        /*
         * Repository path
         *
         * Use the actual cloned repository path
         * stored by your Repository entity.
         */
        String repositoryPath =
                repository.getLocalPath();

        long totalFiles =
                repositoryFileService
                        .countFiles(repositoryPath);

        long classes =
                classMetadataRepository
                        .countByRepositoryId(repositoryId);

        long methods =
                methodMetadataRepository
                        .countByRepositoryId(repositoryId);

        long packages =
                packageMetadataRepository
                        .countByRepositoryId(repositoryId);

        List<String> technologies =
                technologyMetadataRepository
                        .findByRepositoryId(repositoryId)
                        .stream()
                        .map(TechnologyMetadata::getTechnologyName)
                        .toList();

        return RepositorySummaryResponse.builder()
                .repository(repository.getRepoName())
                .totalFiles(totalFiles)
                .classes(classes)
                .methods(methods)
                .packages(packages)
                .technologies(technologies)
                .build();
    }
}