package com.pranay.code_review_platform_backend. security.service;

import com.pranay.code_review_platform_backend.service.TokenEncryptionService;
import com.pranay.code_review_platform_backend.dto.response.GithubRepositoryResponse;
import com.pranay.code_review_platform_backend.entity.GithubUser;
import com.pranay.code_review_platform_backend.repository.GithubUserRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class GithubRepositoryService {

    private final GithubUserRepository githubUserRepository;
    private final TokenEncryptionService tokenEncryptionService;

    private final RestTemplate restTemplate = new RestTemplate();

    public GithubRepositoryService(
            GithubUserRepository githubUserRepository,
            TokenEncryptionService tokenEncryptionService
    ) {
        this.githubUserRepository = githubUserRepository;
        this.tokenEncryptionService = tokenEncryptionService;
    }

    public List<GithubRepositoryResponse> getRepositories(Long githubUserId) {

        GithubUser githubUser = githubUserRepository
                .findById(githubUserId)
                .orElseThrow(() -> new RuntimeException("GitHub user not found"));

        // Decrypt the GitHub access token before sending it to GitHub
        String accessToken =
                tokenEncryptionService.decrypt(githubUser.getAccessToken());

        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(accessToken);
        headers.set("Accept", "application/vnd.github+json");

        HttpEntity<Void> requestEntity =
                new HttpEntity<>(headers);

        String githubApiUrl =
                "https://api.github.com/user/repos";

        ResponseEntity<GithubRepository[]> response =
                restTemplate.exchange(
                        githubApiUrl,
                        HttpMethod.GET,
                        requestEntity,
                        GithubRepository[].class
                );

        GithubRepository[] repositories =
                response.getBody();

        if (repositories == null) {
            return List.of();
        }

        return Arrays.stream(repositories)
                .map(repo -> new GithubRepositoryResponse(
                        repo.name(),
                        repo.owner().login()
                ))
                .toList();
    }

    private record GithubRepository(
            String name,
            Owner owner
    ) {}

    private record Owner(
            String login
    ) {}
}
