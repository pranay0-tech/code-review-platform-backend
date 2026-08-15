package com.pranay.code_review_platform_backend.parser.service;

import com.pranay.code_review_platform_backend.parser.dto.RepositoryHealthResponse;
import org.springframework.stereotype.Service;

@Service
public class RepositoryHealthService {

    public RepositoryHealthResponse generateHealthInsights(
            Long repositoryId
    ) {

        /*
         * These values are temporary.
         * They should be calculated from your stored
         * repository metadata.
         */

        long controllers = 12;
        long services = 15;
        long repositories = 8;
        long entities = 14;

        double averageMethodsPerClass = 6.0;

        return RepositoryHealthResponse.builder()
                .controllers(controllers)
                .services(services)
                .repositories(repositories)
                .entities(entities)
                .averageMethodsPerClass(averageMethodsPerClass)
                .build();
    }
}