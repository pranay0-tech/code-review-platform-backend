package com.pranay.code_review_platform_backend.service.impl;

import com.pranay.code_review_platform_backend.entity.GithubUser;
import com.pranay.code_review_platform_backend.repository.GithubUserRepository;
import com.pranay.code_review_platform_backend.service.GithubUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GithubUserServiceImpl implements GithubUserService {

    private final GithubUserRepository repository;

    @Override
    public GithubUser save(GithubUser githubUser) {
        return repository.save(githubUser);
    }

    @Override
    public Optional<GithubUser> findByGithubId(String githubId) {
        return repository.findByGithubId(githubId);
    }

    @Override
    public Optional<GithubUser> findByGithubUsername(String githubUsername) {
        return repository.findByGithubUsername(githubUsername);
    }
}
