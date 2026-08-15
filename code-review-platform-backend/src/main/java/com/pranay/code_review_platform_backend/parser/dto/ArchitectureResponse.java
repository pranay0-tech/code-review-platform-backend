package com.pranay.code_review_platform_backend.parser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ArchitectureResponse {

    private List<String> controllers;

    private List<String> services;

    private List<String> repositories;

    private List<String> entities;

    private List<String> configurations;
}