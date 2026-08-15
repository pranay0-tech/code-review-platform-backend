package com.pranay.code_review_platform_backend.parser.flow;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FlowStep {

    private int step;

    private String className;

    private String methodName;

    private String description;
}