package com.pranay.code_review_platform_backend.parser.service;

import com.pranay.code_review_platform_backend.parser.dto.DeveloperOnboardingResponse;
import com.pranay.code_review_platform_backend.parser.dto.OnboardingStep;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperOnboardingService {

    public DeveloperOnboardingResponse generateOnboardingGuide(
            Long repositoryId
    ) {

        /*
         * TODO:
         * Replace these with actual repository metadata.
         */

        List<OnboardingStep> startWith = List.of(

                OnboardingStep.builder()
                        .order(1)
                        .className("AuthController")
                        .reason("Main entry point for authentication requests.")
                        .build(),

                OnboardingStep.builder()
                        .order(2)
                        .className("AuthService")
                        .reason("Contains authentication business logic.")
                        .build(),

                OnboardingStep.builder()
                        .order(3)
                        .className("UserRepository")
                        .reason("Handles user data access.")
                        .build()
        );

        List<OnboardingStep> exploreNext = List.of(

                OnboardingStep.builder()
                        .order(4)
                        .className("SecurityConfig")
                        .reason("Defines application security configuration.")
                        .build(),

                OnboardingStep.builder()
                        .order(5)
                        .className("JwtAuthenticationFilter")
                        .reason("Handles JWT authentication for requests.")
                        .build()
        );

        return DeveloperOnboardingResponse.builder()
                .introduction(
                        "Start with the authentication flow, then explore " +
                        "security and the database layer."
                )
                .startWith(startWith)
                .exploreNext(exploreNext)
                .build();
    }
}