package com.pranay.code_review_platform_backend.service;

import com.pranay.code_review_platform_backend.dto.request.LoginRequest;
import com.pranay.code_review_platform_backend.dto.request.RegisterRequest;
import com.pranay.code_review_platform_backend.dto.response.AuthResponse;
import com.pranay.code_review_platform_backend.entity.User;
import com.pranay.code_review_platform_backend.exception.DuplicateResourceException;
import com.pranay.code_review_platform_backend.repository.UserRepository;
import com.pranay.code_review_platform_backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.pranay.code_review_platform_backend.entity.Repository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
    throw new DuplicateResourceException("Email already exists");
}

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        String token = jwtService.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .authorities("USER")
                        .build()
        );

        return new AuthResponse(token);
    }
}