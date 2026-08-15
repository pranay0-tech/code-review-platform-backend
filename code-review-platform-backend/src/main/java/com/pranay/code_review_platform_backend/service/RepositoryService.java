package com.pranay.code_review_platform_backend.service;

import com.pranay.code_review_platform_backend.dto.request.ConnectRepositoryRequest;
import com.pranay.code_review_platform_backend.dto.response.CloneRepositoryResponse;
import com.pranay.code_review_platform_backend.entity.Repository;
import com.pranay.code_review_platform_backend.parser.service.RepositoryParserService;
import com.pranay.code_review_platform_backend.repository.RepositoryRepository;

import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class RepositoryService {

    private final RepositoryRepository repositoryRepository;
    private final GitRepositoryService gitRepositoryService;
    private final RepositoryParserService repositoryParserService;

    // Updated Constructor to inject RepositoryParserService
    public RepositoryService(
            RepositoryRepository repositoryRepository, 
            GitRepositoryService gitRepositoryService,
            RepositoryParserService repositoryParserService) {
        this.repositoryRepository = repositoryRepository;
        this.gitRepositoryService = gitRepositoryService;
        this.repositoryParserService = repositoryParserService;
    }

    public Repository connectRepository(ConnectRepositoryRequest request) {
        String owner = request.getOwnerName() != null ? request.getOwnerName() : "pranay0-tech";
        
        // Auto-generate clone URL if not explicitly provided
        String cloneUrl = request.getCloneUrl() != null 
                ? request.getCloneUrl() 
                : "https://github.com/" + owner + "/" + request.getRepoName() + ".git";

        Repository repository = Repository.builder()
                .repoName(request.getRepoName())
                .ownerName(owner)
                .githubRepoId(request.getGithubRepoId() != null ? request.getGithubRepoId() : 0L)
                .cloneUrl(cloneUrl) // Critical for JGit!
                .defaultBranch(request.getDefaultBranch() != null ? request.getDefaultBranch() : "main")
                .language(request.getLanguage() != null ? request.getLanguage() : "Java")
                .build();

        return repositoryRepository.save(repository);
    }

    public CloneRepositoryResponse cloneRepository(Long repositoryId) throws Exception {
        Repository repository = repositoryRepository.findById(repositoryId)
                .orElseThrow(() -> new RuntimeException("Repository not found with ID: " + repositoryId));

        if (repository.getCloneUrl() == null || repository.getCloneUrl().isEmpty()) {
            throw new IllegalStateException("Cannot clone: cloneUrl is missing for this repository.");
        }

        String localPath = gitRepositoryService.cloneOrUpdateRepository(repository);

        repository.setLocalPath(localPath);
        repositoryRepository.save(repository);

        return new CloneRepositoryResponse("success", localPath);
    }

    // New Method: Handles repo fetching and triggers local file scanning
    public RepositoryParserService.ScanResult scanRepository(Long repositoryId) throws IOException {
        Repository repository = repositoryRepository.findById(repositoryId)
                .orElseThrow(() -> new RuntimeException("Repository not found with ID: " + repositoryId));

        if (repository.getLocalPath() == null || repository.getLocalPath().isBlank()) {
            throw new IllegalStateException("Repository has not been cloned yet. Please clone it before scanning.");
        }

        return repositoryParserService.scanRepository(repository.getLocalPath());
    }

    public Object getRepositoryById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRepositoryById'");
    }
}