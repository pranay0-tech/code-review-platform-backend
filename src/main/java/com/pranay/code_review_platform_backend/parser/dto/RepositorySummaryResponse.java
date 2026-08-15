package com.pranay.code_review_platform_backend.parser.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepositorySummaryResponse {

    private String repository;

    private long totalFiles;

    private long classes;

    private long methods;

    private long packages;

    private List<String> technologies;
}