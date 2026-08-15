package com.pranay.code_review_platform_backend.api;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiDiscoveryService {

    public List<ApiEndpoint> discoverApis() {

        return List.of(
                new ApiEndpoint(
                        "POST",
                        "/api/auth/login"
                ),
                new ApiEndpoint(
                        "POST",
                        "/api/auth/register"
                )
        );
    }
}