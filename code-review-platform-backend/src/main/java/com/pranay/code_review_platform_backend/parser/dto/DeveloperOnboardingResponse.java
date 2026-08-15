package com.pranay.code_review_platform_backend.parser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class DeveloperOnboardingResponse {

    private String introduction;

    private List<OnboardingStep> startWith;

    private List<OnboardingStep> exploreNext;
}