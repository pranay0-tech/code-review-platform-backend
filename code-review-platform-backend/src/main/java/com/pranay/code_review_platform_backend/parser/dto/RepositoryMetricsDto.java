package com.pranay.code_review_platform_backend.parser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RepositoryMetricsDto {

    private long controllers;
    private long services;
    private long repositories;
    private long entities;
    private long totalClasses;
    private long totalMethods;
    private double averageMethodsPerClass;
}