package com.pranay.code_review_platform_backend.security.service;

import com.pranay.code_review_platform_backend.entity.GithubUser;

import java.util.Optional;

public interface GithubUserService {

    GithubUser save(GithubUser githubUser);

    Optional<GithubUser> findByGithubId(String githubId);

    Optional<GithubUser> findByGithubUsername(String githubUsername);
}
