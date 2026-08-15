package com.pranay.code_review_platform_backend.dto.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AIChunkResult {

    private String chunkId;

    @JsonProperty("class")
    private String className;

    private String method;
    private String file;
    private String content;
    private Double distance;
}