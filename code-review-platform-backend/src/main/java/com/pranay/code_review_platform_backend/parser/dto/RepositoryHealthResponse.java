package com.pranay.code_review_platform_backend.parser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RepositoryHealthResponse {

    private long controllers;

    private long services;

    private long repositories;

    private long entities;

    private double averageMethodsPerClass;
}