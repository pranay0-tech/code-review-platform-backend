package com.pranay.code_review_platform_backend.parser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EndpointMetadata {
    private String controllerName;
    private String httpMethod;      // e.g., POST, GET, PUT, DELETE
    private String fullPath;        // Combined path: e.g., /api/auth/login
    private String methodName;      // Java method name: e.g., login
}