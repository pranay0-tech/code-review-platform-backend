package com.pranay.code_review_platform_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "github_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GithubUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String githubId;

    @Column(nullable = false, unique = true)
    private String githubUsername;

    @Column(unique = true)
    private String githubEmail;

    private String avatarUrl;

    @Column(length = 2000)
    private String accessToken;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
