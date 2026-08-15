package com.pranay.code_review_platform_backend.service;

import com.pranay.code_review_platform_backend.parser.model.ParsingJob;
import com.pranay.code_review_platform_backend.parser.model.ParsingStatus;
import com.pranay.code_review_platform_backend.parser.service.ClassExtractionService;
import com.pranay.code_review_platform_backend.parser.service.ImportExtractionService;
import com.pranay.code_review_platform_backend.parser.service.MethodExtractionService;
import com.pranay.code_review_platform_backend.parser.service.PackageExtractionService;
import com.pranay.code_review_platform_backend.repository.ParsingJobRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RepositoryParsingService {

    private final ParsingJobRepository parsingJobRepository;
    private final ClassExtractionService classExtractionService;
    private final MethodExtractionService methodExtractionService;
    private final ImportExtractionService importExtractionService;
    private final PackageExtractionService packageExtractionService;
    private final TechnologyExtractionService technologyExtractionService;

    public RepositoryParsingService(
            ParsingJobRepository parsingJobRepository,
            ClassExtractionService classExtractionService,
            MethodExtractionService methodExtractionService,
            ImportExtractionService importExtractionService,
            PackageExtractionService packageExtractionService,
            TechnologyExtractionService technologyExtractionService) {

        this.parsingJobRepository = parsingJobRepository;
        this.classExtractionService = classExtractionService;
        this.methodExtractionService = methodExtractionService;
        this.importExtractionService = importExtractionService;
        this.packageExtractionService = packageExtractionService;
        this.technologyExtractionService = technologyExtractionService;
    }

    @Async
    public void parseRepository(
            Long jobId,
            String repositoryPath,
            Long repositoryId) {

        ParsingJob job =
                parsingJobRepository.findById(jobId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Parsing job not found"
                                )
                        );

        try {

            job.setStatus(ParsingStatus.PARSING);
            job.setStartedAt(LocalDateTime.now());

            parsingJobRepository.save(job);

            /*
             * Run repository analysis
             */

            classExtractionService.extractClasses(
                    repositoryPath,
                    repositoryId
            );

            methodExtractionService.extractMethods(
                    repositoryPath,
                    repositoryId
            );

            importExtractionService.extractImports(
                    repositoryPath,
                    repositoryId
            );

            packageExtractionService.extractPackages(
                    repositoryPath,
                    repositoryId
            );

            technologyExtractionService.extractTechnologies(
                    repositoryPath,
                    repositoryId
            );

            /*
             * Parsing completed
             */

            job.setStatus(ParsingStatus.COMPLETED);
            job.setCompletedAt(LocalDateTime.now());

            parsingJobRepository.save(job);

        } catch (Exception e) {

            job.setStatus(ParsingStatus.FAILED);
            job.setErrorMessage(e.getMessage());
            job.setCompletedAt(LocalDateTime.now());

            parsingJobRepository.save(job);
        }
    }
}