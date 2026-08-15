package com.pranay.code_review_platform_backend.dto.ai;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AISearchApiRequest {

    private Long repositoryId;
    private String query;
}
