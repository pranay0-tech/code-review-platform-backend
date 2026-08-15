package com.pranay.code_review_platform_backend.parser.dto;

import com.pranay.code_review_platform_backend.parser.model.ParsingStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StartParsingResponse {

    private Long repositoryId;

    private Long jobId;

    private ParsingStatus status;
}