package com.pranay.code_review_platform_backend.repository;

import com.pranay.code_review_platform_backend.entity.GithubUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GithubUserRepository extends JpaRepository<GithubUser, Long> {

    Optional<GithubUser> findByGithubId(String githubId);

    Optional<GithubUser> findByGithubUsername(String githubUsername);

    Optional<GithubUser> findByGithubEmail(String githubEmail);
}
