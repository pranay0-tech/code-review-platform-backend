package com.pranay.code_review_platform_backend.parser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParsedEndpointDto {
    private String controllerClass;
    private String basePath;
    private String method;
    private String path;
    private String fullPath;
    private String methodName;
}