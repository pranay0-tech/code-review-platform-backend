package com.pranay.code_review_platform_backend.parser.service;

import com.pranay.code_review_platform_backend.parser.dto.ProjectOverviewResponse;
import org.springframework.stereotype.Service;

@Service
public class ProjectSummarizer {

    public ProjectOverviewResponse generateOverview(
            String classMetadata,
            String packageMetadata,
            String technologyMetadata
    ) {

        String prompt = buildPrompt(
                classMetadata,
                packageMetadata,
                technologyMetadata
        );

        // LLM call will be added here

        String overview = "Project overview generated from repository metadata.";

        return new ProjectOverviewResponse(overview);
    }

    private String buildPrompt(
            String classMetadata,
            String packageMetadata,
            String technologyMetadata
    ) {

        return """
                You are a senior software architect.

                Analyze the following repository metadata and create
                a high-level project overview.

                Include:

                1. Project Overview
                2. Architecture
                3. Technology Stack
                4. Major Modules
                5. Authentication
                6. Database
                7. External Integrations

                Class Metadata:
                %s

                Package Metadata:
                %s

                Technology Metadata:
                %s

                Do not invent information that is not present
                in the repository metadata.
                """.formatted(
                classMetadata,
                packageMetadata,
                technologyMetadata
        );
    }
}