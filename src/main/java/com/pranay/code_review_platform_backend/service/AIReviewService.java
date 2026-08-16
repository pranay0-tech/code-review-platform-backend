package main.java.com.pranay.code_review_platform_backend.service;

import org.springframework.stereotype.Service;

@Service
public class AIReviewService {

    private final AIEngineService aiEngineService;

    public AIReviewService(AIEngineService aiEngineService) {
        this.aiEngineService = aiEngineService;
    }

    public String generateSuggestion(String code) {

        String prompt = """
                You are a senior software engineer.

                Review the following Java code.

                Identify:
                1. Maintainability issues
                2. Design issues
                3. Refactoring opportunities

                Provide concise recommendations.

                Code:
                %s
                """.formatted(code);

        return aiEngineService.generateResponse(prompt);
    }
}