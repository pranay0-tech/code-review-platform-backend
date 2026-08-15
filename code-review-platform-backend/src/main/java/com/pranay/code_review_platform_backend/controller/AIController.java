package com.pranay.code_review_platform_backend.controller;

import com.pranay.code_review_platform_backend.dto.ai.AIChunkResult;
import com.pranay.code_review_platform_backend.dto.ai.AISearchApiRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIClientService aiClientService;

    @PostMapping("/search")
    public List<AIChunkResult> search(
            @RequestBody AISearchApiRequest request
    ) {
        return aiClientService.search(
                request.getRepositoryId(),
                request.getQuery()
        );
    }
}
