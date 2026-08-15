package com.pranay.code_review_platform_backend.parser.flow;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlowAnalyzer {

    public FlowResult analyzeLoginFlow() {

        List<FlowStep> steps = new ArrayList<>();

        steps.add(new FlowStep(
                1,
                "AuthController",
                "login",
                "Receives the login request"
        ));

        steps.add(new FlowStep(
                2,
                "AuthService",
                "authenticate",
                "Validates the user's credentials"
        ));

        steps.add(new FlowStep(
                3,
                "UserRepository",
                "findByEmail",
                "Fetches the user from the database"
        ));

        steps.add(new FlowStep(
                4,
                "AuthService",
                "generateToken",
                "Generates the JWT token"
        ));

        steps.add(new FlowStep(
                5,
                "AuthController",
                "login",
                "Returns the authentication response"
        ));

        List<String> classes = List.of(
                "AuthController",
                "AuthService",
                "UserRepository"
        );

        return new FlowResult(
                "LOGIN_FLOW",
                steps,
                classes
        );
    }
}