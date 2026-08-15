package com.pranay.code_review_platform_backend.service;

import com.pranay.code_review_platform_backend.entity.Repository;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service; // <-- Make sure this is imported

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service // <-- ADD THIS ANNOTATION IF IT IS MISSING!
public class GitRepositoryService {

    private static final String BASE_DIR = "repositories";

    public String cloneOrUpdateRepository(Repository repo) throws GitAPIException, IOException {
        Path targetDir = Path.of(BASE_DIR, repo.getRepoName());

        Files.createDirectories(Path.of(BASE_DIR));
        File repoFolder = targetDir.toFile();

        if (repoFolder.exists() && repoFolder.list() != null && repoFolder.list().length > 0) {
            try (Git git = Git.open(repoFolder)) {
                git.pull().call();
            }
        } else {
            Git.cloneRepository()
                    .setURI(repo.getCloneUrl())
                    .setDirectory(repoFolder)
                    .setBranch(repo.getDefaultBranch() != null ? repo.getDefaultBranch() : "main")
                    .call()
                    .close();
        }

        return targetDir.toString().replace("\\", "/");
    }
}
