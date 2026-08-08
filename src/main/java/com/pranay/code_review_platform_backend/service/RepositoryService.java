package com.pranay.code_review_platform_backend.service;

import com.pranay.code_review_platform_backend.dto.request.ConnectRepositoryRequest;
import com.pranay.code_review_platform_backend.entity.Repository;
import com.pranay.code_review_platform_backend.repository.RepositoryRepository;
import org.springframework.stereotype.Service;

@Service
public class RepositoryService {

    private final RepositoryRepository repositoryRepository;

    public RepositoryService(RepositoryRepository repositoryRepository) {
        this.repositoryRepository = repositoryRepository;
    }

    public Repository connectRepository(ConnectRepositoryRequest request) {

        Repository repository = Repository.builder()
                .repoName(request.getRepoName())
                .ownerName("pranay0-tech")
                .build();

        return repositoryRepository.save(repository);
    }
}