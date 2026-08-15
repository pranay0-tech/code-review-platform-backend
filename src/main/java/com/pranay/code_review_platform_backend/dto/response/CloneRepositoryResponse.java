package com.pranay.code_review_platform_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CloneRepositoryResponse {

    private String status;
    private String path;
}