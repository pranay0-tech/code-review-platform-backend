package com.pranay.code_review_platform_backend.parser.service;

import com.pranay.code_review_platform_backend.parser.dto.ParsingJobResponse;
import com.pranay.code_review_platform_backend.parser.model.ParsingJob;
import com.pranay.code_review_platform_backend.repository.ParsingJobRepository;
import org.springframework.stereotype.Service;

@Service
public class ParsingJobService {

    private final ParsingJobRepository parsingJobRepository;

    public ParsingJobService(
            ParsingJobRepository parsingJobRepository) {

        this.parsingJobRepository = parsingJobRepository;
    }

    public ParsingJobResponse getJobStatus(Long jobId) {

        ParsingJob job =
                parsingJobRepository.findById(jobId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Parsing job not found"
                                )
                        );

        return ParsingJobResponse.builder()
                .jobId(job.getId())
                .repositoryId(job.getRepositoryId())
                .status(job.getStatus())
                .errorMessage(job.getErrorMessage())
                .build();
    }
}