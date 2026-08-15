package com.pranay.code_review_platform_backend.parser.flow;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FlowResult {

    private String flowName;

    private List<FlowStep> steps;

    private List<String> classes;
}