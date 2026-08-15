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
@ToString
public class OnboardingStepDto {

    private int stepOrder;
    private String className;
    private String classType;
    private String packageName;
    private String rationale;
    private List<String> dependencies;
}