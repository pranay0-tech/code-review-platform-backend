package com.pranay.code_review_platform_backend.service;

import com.pranay.code_review_platform_backend.parser.dto.ChatMessageResponse;
import com.pranay.code_review_platform_backend.parser.dto.SourceResponse;
import com.pranay.code_review_platform_backend.entity.ApiEndpoint;
import com.pranay.code_review_platform_backend.repository.ApiEndpointRepository;
import com.pranay.code_review_platform_backend.util.ControllerEntityResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiDiscoveryService {

    private final ApiEndpointRepository apiEndpointRepository;
    private final ControllerEntityResolver entityResolver;

    @Transactional(readOnly = true)
    public ChatMessageResponse handleApiDiscovery(Long repositoryId, String userMessage) {
        // 1. Fetch all stored endpoints for this repository
        List<ApiEndpoint> allEndpoints = apiEndpointRepository.findByRepositoryId(repositoryId);

        // 2. Intelligently filter by target Controller (e.g. "User APIs" -> UserController)
        List<ApiEndpoint> targetedEndpoints = entityResolver.filterByTargetEntity(allEndpoints, userMessage);

        if (targetedEndpoints.isEmpty()) {
            return ChatMessageResponse.builder()
                    .answer("No matching API endpoints were found for your request.")
                    .sources(List.of())
                    .build();
        }

        // 3. Format dynamic markdown response
        StringBuilder answer = new StringBuilder("Found the following API endpoints:\n\n");
        for (ApiEndpoint ep : targetedEndpoints) {
            answer.append(String.format("* `%s %s` → `%s#%s()`\n",
                    ep.getHttpMethod(),
                    ep.getFullPath(),
                    ep.getControllerClass(),
                    ep.getMethodName()));
        }

        // 4. Map sources for frontend attribution without casting
        List<SourceResponse> sources = targetedEndpoints.stream()
                .map(ep -> SourceResponse.builder()
                        .fileName(ep.getControllerClass() + ".java")
                        .className(ep.getControllerClass())
                        .methodName(ep.getMethodName())
                        .build())
                .distinct()
                .toList();

        // 5. Construct and return response
        return ChatMessageResponse.builder()
                .answer(answer.toString())
                .sources(sources)
                .build();
    }
}