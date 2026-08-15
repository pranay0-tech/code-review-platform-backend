package com.pranay.code_review_platform_backend.util;

import com.pranay.code_review_platform_backend.parser.dto.ApiRouteDto;

public class PathResolverUtils {

    /**
     * Combines a class-level base path and a method-level subpath into a single clean URL path.
     * 
     * @param basePath    e.g., "/api/users"
     * @param subPath     e.g., "/{id}" or "" or null
     * @param httpMethod  e.g., "GET", "POST"
     * @return ApiRouteDto containing the HTTP method and full combined path.
     */
    public static ApiRouteDto createRoute(String basePath, String subPath, String httpMethod) {
        String cleanBase = sanitizePath(basePath);
        String cleanSub = sanitizePath(subPath);

        String fullPath;

        if (cleanBase.isEmpty() && cleanSub.isEmpty()) {
            fullPath = "/";
        } else if (cleanSub.isEmpty()) {
            fullPath = cleanBase;
        } else if (cleanBase.isEmpty()) {
            fullPath = cleanSub;
        } else {
            // Remove trailing slash from base and ensure sub starts with a slash
            if (cleanBase.endsWith("/")) {
                cleanBase = cleanBase.substring(0, cleanBase.length() - 1);
            }
            if (!cleanSub.startsWith("/")) {
                cleanSub = "/" + cleanSub;
            }
            fullPath = cleanBase + cleanSub;
        }

        return ApiRouteDto.builder()
                .method(httpMethod)
                .path(fullPath)
                .build();
    }

    private static String sanitizePath(String path) {
        if (path == null) {
            return "";
        }
        String trimmed = path.trim().replace("\"", "");
        if (trimmed.isEmpty() || trimmed.equals("/")) {
            return "";
        }
        return trimmed.startsWith("/") ? trimmed : "/" + trimmed;
    }
}