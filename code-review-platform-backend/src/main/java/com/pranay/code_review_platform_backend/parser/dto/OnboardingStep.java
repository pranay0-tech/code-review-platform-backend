package com.pranay.code_review_platform_backend.parser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OnboardingStep {

    private int order;

    private String className;

    private String reason;
    private String classType;
    private String packageName;
    private String rationale;
    private long referenceCount;
    
}

