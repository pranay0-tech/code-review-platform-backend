package com.pranay.code_review_platform_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GithubRepositoryResponse {

    private String name;
    private String owner;
}
