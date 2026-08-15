package com.pranay.code_review_platform_backend.util;

import com.pranay.code_review_platform_backend.entity.ApiEndpoint;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ControllerEntityResolver {

    /**
     * Identifies target controllers matching the domain entity referenced in the user's prompt.
     */
    public List<ApiEndpoint> filterByTargetEntity(List<ApiEndpoint> endpoints, String userMessage) {
        if (endpoints.isEmpty()) {
            return endpoints;
        }

        // 1. Extract candidate entities from prompt (e.g. "show user apis" -> "user")
        String targetEntity = extractEntityKeyword(userMessage);

        if (targetEntity.isEmpty()) {
            return endpoints; // Return all endpoints if no specific domain entity was specified
        }

        // 2. Find exact or fuzzy Controller matches (e.g., "user" -> "UserController")
        Set<String> matchingControllers = endpoints.stream()
                .map(ApiEndpoint::getControllerClass)
                .filter(controller -> matchesEntity(controller, targetEntity))
                .collect(Collectors.toSet());

        // 3. Fallback to matching full path if controller name match fails (e.g., /api/users)
        if (matchingControllers.isEmpty()) {
            return endpoints.stream()
                    .filter(ep -> ep.getFullPath().toLowerCase().contains(targetEntity))
                    .toList();
        }

        // 4. Return endpoints owned by the target Controller(s)
        return endpoints.stream()
                .filter(ep -> matchingControllers.contains(ep.getControllerClass()))
                .toList();
    }

    private String extractEntityKeyword(String prompt) {
        String cleaned = prompt.toLowerCase()
                .replaceAll("(?i)\\b(show|list|get|all|the|apis|api|endpoints|endpoint|routes|route|for|me)\\b", "")
                .trim();
        
        // Remove trailing plural 's' for normalized matching (e.g., "users" -> "user", "payments" -> "payment")
        if (cleaned.endsWith("s") && cleaned.length() > 3) {
            cleaned = cleaned.substring(0, cleaned.length() - 1);
        }
        return cleaned;
    }

    private boolean matchesEntity(String controllerClass, String targetEntity) {
        String lowerClass = controllerClass.toLowerCase();
        // E.g. "usercontroller" contains "user"
        return lowerClass.contains(targetEntity);
    }
}