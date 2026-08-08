package com.pranay.code_review_platform_backend.controller;

import com.pranay.code_review_platform_backend.dto.request.LoginRequest;
import com.pranay.code_review_platform_backend.dto.request.RegisterRequest;
import com.pranay.code_review_platform_backend.dto.response.AuthResponse;
import com.pranay.code_review_platform_backend.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
