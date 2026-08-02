package com.pranay.code_review_platform_backend.dto.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;

    private String password;

}
