package com.pranay.code_review_platform_backend.parser.service;

import com.pranay.code_review_platform_backend.dto.ai.AIChunkResult;
import com.pranay.code_review_platform_backend.dto.ai.AISearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AIClientService {

    private final WebClient webClient;

    @Value("${ai.service.url}")
    private String aiServiceUrl;

    public List<AIChunkResult> search(
            Long repositoryId,
            String query
    ) {

        AISearchRequest request = new AISearchRequest(
                repositoryId,
                query
        );

        return webClient
                .post()
                .uri(aiServiceUrl + "/search")
                .bodyValue(request)
                .retrieve()
                .bodyToFlux(AIChunkResult.class)
                .collectList()
                .block();
    }
}