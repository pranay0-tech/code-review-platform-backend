package com.pranay.code_review_platform_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectRepositoryRequest {

    @NotBlank(message = "Repository name is required")
    private String repoName;

    private String ownerName;
    private Long githubRepoId;
    private String cloneUrl;
    private String defaultBranch;
    private String language;
}
