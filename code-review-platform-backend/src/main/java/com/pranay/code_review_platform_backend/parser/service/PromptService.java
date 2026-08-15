package com.pranay.code_review_platform_backend.parser.service;

import com.pranay.code_review_platform_backend.parser.model.IntentType;
import com.pranay.code_review_platform_backend.parser.prompt.PromptTemplates;
import org.springframework.stereotype.Service;

@Service
public class PromptService {

    public String buildPrompt(
            IntentType intent,
            String repositoryContext,
            String conversationHistory,
            String question
    ) {

        String template = getTemplate(intent);

        return String.format(
                template,
                repositoryContext,
                conversationHistory,
                question
        );
    }

    private String getTemplate(IntentType intent) {

        return switch (intent) {

            case FLOW_ANALYSIS ->
                    PromptTemplates.FLOW_ANALYSIS;

            case API_DISCOVERY ->
                    PromptTemplates.API_DISCOVERY;

            case CLASS_EXPLANATION ->
                    PromptTemplates.CLASS_EXPLANATION;

            case METHOD_EXPLANATION ->
                    PromptTemplates.METHOD_EXPLANATION;

            case DEPENDENCY_ANALYSIS ->
                    PromptTemplates.DEPENDENCY_ANALYSIS;

            case TECH_STACK_ANALYSIS ->
                    PromptTemplates.TECH_STACK_ANALYSIS;

            case GENERAL_QA ->
                    PromptTemplates.GENERAL_QA;
        };
    }
}