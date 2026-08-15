package com.pranay.code_review_platform_backend.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiEndpoint {

    private String method;

    private String path;
}