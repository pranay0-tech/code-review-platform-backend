package com.pranay.code_review_platform_backend.parser.service;

import com.pranay.code_review_platform_backend.parser.model.ProjectStructure;
import com.pranay.code_review_platform_backend.repository.ProjectStructureRepository;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProjectStructureService {

    private final ProjectStructureRepository projectStructureRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Directory names to skip when generating the tree
    private static final Set<String> IGNORED_DIRS = Set.of(
            ".git", "target", "node_modules", ".idea", ".settings", "bin", "build", ".gradle"
    );

    @Transactional
    public ProjectStructure generateAndSaveStructure(String repositoryPath, Long repositoryId) throws IOException {
        File rootDir = new File(repositoryPath);
        if (!rootDir.exists() || !rootDir.isDirectory()) {
            throw new IllegalArgumentException("Invalid repository path: " + repositoryPath);
        }

        // Build nested Map representation
        Map<String, Object> treeMap = buildTree(rootDir);

        // Convert Map to JSON String
        String jsonStructure = objectMapper.writeValueAsString(treeMap);

        // Save or Update in Database
        ProjectStructure structure = projectStructureRepository.findByRepositoryId(repositoryId)
                .orElse(ProjectStructure.builder().repositoryId(repositoryId).build());

        structure.setStructureJson(jsonStructure);
        return projectStructureRepository.save(structure);
    }

    private Map<String, Object> buildTree(File folder) {
        Map<String, Object> node = new LinkedHashMap<>();
        File[] files = folder.listFiles();

        if (files != null) {
            // Sort to keep folders neatly ordered
            Arrays.sort(files, Comparator.comparing(File::getName));

            for (File file : files) {
                if (file.isDirectory()) {
                    if (IGNORED_DIRS.contains(file.getName()) || file.getName().startsWith(".")) {
                        continue; // Skip build/metadata folders
                    }
                    // Recursively process child directories
                    node.put(file.getName(), buildTree(file));
                }
            }
        }

        return node;
    }
}