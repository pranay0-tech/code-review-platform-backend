package com.pranay.code_review_platform_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @GetMapping("/profile")
    public String profile() {
        return "Welcome to your profile!";
    }
}
