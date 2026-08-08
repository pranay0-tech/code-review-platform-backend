

package com.pranay.code_review_platform_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConnectRepositoryRequest {

    @NotBlank(message = "Repository name is required")
    private String repoName;

    private String repoUrl;
}
