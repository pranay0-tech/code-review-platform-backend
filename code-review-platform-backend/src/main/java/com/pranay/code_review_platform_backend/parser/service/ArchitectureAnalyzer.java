package com.pranay.code_review_platform_backend.parser.service;

import com.pranay.code_review_platform_backend.parser.dto.ArchitectureResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArchitectureAnalyzer {

    public ArchitectureResponse analyzeArchitecture(
            Long repositoryId
    ) {

        /*
         * TODO:
         * Replace these lists with data retrieved from
         * the repository's stored class metadata.
         */

        List<String> controllers = List.of(
                "AuthController",
                "UserController"
        );

        List<String> services = List.of(
                "AuthService",
                "UserService"
        );

        List<String> repositories = List.of(
                "UserRepository"
        );

        List<String> entities = List.of(
                "User",
                "GithubUser"
        );

        List<String> configurations = List.of(
                "SecurityConfig"
        );

        return ArchitectureResponse.builder()
                .controllers(controllers)
                .services(services)
                .repositories(repositories)
                .entities(entities)
                .configurations(configurations)
                .build();
    }
}