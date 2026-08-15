package com.pranay.code_review_platform_backend.parser.service;

import com.pranay.code_review_platform_backend.parser.model.IntentType;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

@Service
public class IntentDetector {

    public IntentType detectIntent(String question) {

        String q = question.toLowerCase();

        if (q.contains("api") ||
            q.contains("endpoint") ||
            q.contains("rest")) {

            return IntentType.API_DISCOVERY;
        }

        if (q.contains("dependency") ||
            q.contains("depends on") ||
            q.contains("used by")) {

            return IntentType.DEPENDENCY_ANALYSIS;
        }

        if (q.contains("technology") ||
            q.contains("technologies") ||
            q.contains("tech stack") ||
            q.contains("framework")) {

            return IntentType.TECH_STACK_ANALYSIS;
        }

        if (q.contains("flow") ||
            q.contains("workflow") ||
            q.contains("how does") ||
            q.contains("process")) {

            return IntentType.FLOW_ANALYSIS;
        }

        if (q.contains("method") ||
            q.contains("function")) {

            return IntentType.METHOD_EXPLANATION;
        }

        if (q.contains("new to this project") ||
    q.contains("where should i start") ||
    q.contains("where do i start") ||
    q.contains("how do i understand this project") ||
    q.contains("onboarding") ||
    q.contains("new developer")) {

    return IntentType.DEVELOPER_ONBOARDING;
    }

        if (q.contains("class") ||
            q.contains("what does") ||
            q.contains("explain")) {

            return IntentType.CLASS_EXPLANATION;
        }

        return IntentType.GENERAL_QA;

        

    }
}