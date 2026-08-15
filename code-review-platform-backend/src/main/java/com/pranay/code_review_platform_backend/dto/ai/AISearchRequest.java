package com.pranay.code_review_platform_backend.dto.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AISearchRequest {

    private Long repositoryId;
    private String query;
}