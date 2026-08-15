package com.pranay.code_review_platform_backend.parser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OnboardingGuideResponse {

    private Long repositoryId;
    private List<OnboardingStepDto> entryPoints;
    private List<OnboardingStepDto> keyBusinessFlows;
    private List<OnboardingStepDto> infrastructureClasses;
    private List<OnboardingStepDto> coreDependencyHubs;
    private List<OnboardingStepDto> prioritizedFlow;
}






