package com.pranay.code_review_platform_backend.service;

import com.pranay.code_review_platform_backend.parser.model.TechnologyMetadata;
import com.pranay.code_review_platform_backend.repository.TechnologyMetadataRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TechnologyExtractionService {

    private final TechnologyMetadataRepository technologyMetadataRepository;

    public TechnologyExtractionService(
            TechnologyMetadataRepository technologyMetadataRepository) {

        this.technologyMetadataRepository =
                technologyMetadataRepository;
    }

    public List<String> extractTechnologies(
            String repositoryPath,
            Long repositoryId) throws IOException {

        Path root = Paths.get(repositoryPath);

        List<String> technologies = new ArrayList<>();

        /*
         * pom.xml
         */
        Path pomFile = root.resolve("pom.xml");

        if (Files.exists(pomFile)) {

            String pomContent =
                    Files.readString(pomFile);

            detect(pomContent, "spring-boot", "Spring Boot",
                    technologies);

            detect(pomContent, "spring-security",
                    "Spring Security", technologies);

            detect(pomContent, "spring-data-jpa",
                    "JPA", technologies);

            detect(pomContent, "hibernate",
                    "Hibernate", technologies);

            detect(pomContent, "kafka",
                    "Kafka", technologies);

            detect(pomContent, "redis",
                    "Redis", technologies);

            detect(pomContent, "postgresql",
                    "PostgreSQL", technologies);

            detect(pomContent, "jjwt",
                    "JWT", technologies);
        }

        /*
         * Docker
         */
        if (Files.exists(root.resolve("Dockerfile")) ||
                Files.exists(root.resolve("docker-compose.yml")) ||
                Files.exists(root.resolve("docker-compose.yaml"))) {

            technologies.add("Docker");
        }

        /*
         * Remove duplicates
         */
        technologies = technologies.stream()
                .distinct()
                .toList();

        /*
         * Store technologies
         */
        for (String technology : technologies) {

            boolean exists =
                    technologyMetadataRepository
                            .findByRepositoryIdAndTechnologyName(
                                    repositoryId,
                                    technology
                            )
                            .isPresent();

            if (!exists) {

                TechnologyMetadata metadata =
                        TechnologyMetadata.builder()
                                .repositoryId(repositoryId)
                                .technologyName(technology)
                                .build();

                technologyMetadataRepository.save(metadata);
            }
        }

        return technologies;
    }

    private void detect(
            String content,
            String keyword,
            String technology,
            List<String> technologies) {

        if (content.toLowerCase()
                .contains(keyword.toLowerCase())) {

            technologies.add(technology);
        }
    }
}
