package com.pranay.code_review_platform_backend.controller;

import com.pranay.code_review_platform_backend.parser.dto.ArchitectureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/repositories")
@RequiredArgsConstructor
public class ArchitectureController {

    private final ArchitectureAnalyzer architectureAnalyzer;

    @GetMapping("/{id}/architecture")
    public ArchitectureResponse getArchitecture(
            @PathVariable Long id
    ) {

        return architectureAnalyzer.analyzeArchitecture(id);
    }
}