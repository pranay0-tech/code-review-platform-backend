package com.pranay.code_review_platform_backend.parser.service;

import com.pranay.code_review_platform_backend.parser.model.ProjectMetadata;
import com.pranay.code_review_platform_backend.repository.ProjectMetadataRepository;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ProjectDetectionService {

    private final ProjectMetadataRepository projectMetadataRepository;

    public ProjectDetectionService(
            ProjectMetadataRepository projectMetadataRepository) {

        this.projectMetadataRepository =
                projectMetadataRepository;
    }

    public ProjectMetadata detectProject(
            String repositoryPath,
            Long repositoryId) {

        Path root = Path.of(repositoryPath);

        String language = "Unknown";
        String framework = "Unknown";
        String buildTool = "Unknown";

        /*
         * Java + Maven
         */
        if (Files.exists(root.resolve("pom.xml"))) {

            language = "Java";
            buildTool = "Maven";

            String pomContent;

            try {
                pomContent = Files.readString(
                        root.resolve("pom.xml")
                );

                if (pomContent.contains(
                        "spring-boot-starter")) {

                    framework = "Spring Boot";

                } else if (pomContent.contains(
                        "spring-boot")) {

                    framework = "Spring Boot";
                }

            } catch (Exception e) {

                System.err.println(
                        "Unable to read pom.xml"
                );
            }
        }

        /*
         * Java + Gradle
         */
        else if (
                Files.exists(root.resolve("build.gradle")) ||
                Files.exists(root.resolve("build.gradle.kts"))
        ) {

            language = "Java";
            buildTool = "Gradle";

            try {

                Path gradleFile =
                        Files.exists(root.resolve("build.gradle"))
                                ? root.resolve("build.gradle")
                                : root.resolve("build.gradle.kts");

                String content =
                        Files.readString(gradleFile);

                if (content.contains(
                        "spring-boot")) {

                    framework = "Spring Boot";
                }

            } catch (Exception e) {

                System.err.println(
                        "Unable to read Gradle file"
                );
            }
        }

        /*
         * JavaScript / Node.js
         */
        else if (Files.exists(root.resolve("package.json"))) {

            language = "JavaScript";
            buildTool = "npm";

            try {

                String packageJson =
                        Files.readString(
                                root.resolve("package.json")
                        );

                if (packageJson.contains(
                        "\"next\"")) {

                    framework = "Next.js";

                } else if (packageJson.contains(
                        "\"react\"")) {

                    framework = "React";

                } else if (packageJson.contains(
                        "\"express\"")) {

                    framework = "Express";

                } else if (packageJson.contains(
                        "\"@angular/core\"")) {

                    framework = "Angular";
                }

            } catch (Exception e) {

                System.err.println(
                        "Unable to read package.json"
                );
            }
        }

        /*
         * Python
         */
        else if (
                Files.exists(root.resolve("requirements.txt"))
        ) {

            language = "Python";

            try {

                String requirements =
                        Files.readString(
                                root.resolve("requirements.txt")
                        ).toLowerCase();

                if (requirements.contains("fastapi")) {

                    framework = "FastAPI";

                } else if (requirements.contains("django")) {

                    framework = "Django";

                } else if (requirements.contains("flask")) {

                    framework = "Flask";
                }

            } catch (Exception e) {

                System.err.println(
                        "Unable to read requirements.txt"
                );
            }
        }

        ProjectMetadata metadata =
                ProjectMetadata.builder()
                        .repositoryId(repositoryId)
                        .language(language)
                        .framework(framework)
                        .buildTool(buildTool)
                        .build();

        return projectMetadataRepository.save(metadata);
    }
}
