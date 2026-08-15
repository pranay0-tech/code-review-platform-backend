package com.pranay.code_review_platform_backend.parser.dto;

import com.pranay.code_review_platform_backend.parser.model.ParsingStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParsingJobResponse {

    private Long jobId;

    private Long repositoryId;

    private ParsingStatus status;

    private String errorMessage;
}