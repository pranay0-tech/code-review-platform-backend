package com.pranay.code_review_platform_backend.controller;

import com.pranay.code_review_platform_backend.parser.dto.DeveloperOnboardingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/repositories")
@RequiredArgsConstructor
public class DeveloperOnboardingController {

    private final DeveloperOnboardingService onboardingService;

    @GetMapping("/{id}/onboarding")
    public DeveloperOnboardingResponse getOnboardingGuide(
            @PathVariable Long id
    ) {

        return onboardingService.generateOnboardingGuide(id);
    }
}