package com.pranay.code_review_platform_backend.util;

import java.util.Map;
import java.util.Optional;

public class SpringMappingUtils {

    // Direct mapping from Spring annotation simple names to HTTP verbs
    private static final Map<String, String> ANNOTATION_TO_HTTP_METHOD = Map.of(
            "GetMapping", "GET",
            "PostMapping", "POST",
            "PutMapping", "PUT",
            "DeleteMapping", "DELETE",
            "PatchMapping", "PATCH"
    );

    /**
     * Converts a Spring mapping annotation name into its corresponding HTTP Method.
     * 
     * @param annotationName e.g., "PostMapping", "GetMapping"
     * @return Optional containing "POST", "GET", etc.
     */
    public static Optional<String> resolveHttpMethod(String annotationName) {
        return Optional.ofNullable(ANNOTATION_TO_HTTP_METHOD.get(annotationName));
    }
}