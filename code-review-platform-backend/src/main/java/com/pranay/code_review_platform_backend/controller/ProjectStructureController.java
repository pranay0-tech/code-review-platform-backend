package com.pranay.code_review_platform_backend.controller;

import com.pranay.code_review_platform_backend.parser.model.ProjectStructure;
import com.pranay.code_review_platform_backend.parser.service.ProjectStructureService;
import com.pranay.code_review_platform_backend.repository.ProjectStructureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/repositories")
@RequiredArgsConstructor
public class ProjectStructureController<projectStructureRepository, projectStructureRepository1> {

    private final ProjectStructureService projectStructureService;
    private final ProjectStructureRepository projectStructureRepository;

    @PostMapping("/{id}/structure/generate")
    public ResponseEntity<ProjectStructure> generateStructure(
            @PathVariable Long id,
            @RequestParam String path) throws IOException {
        
        ProjectStructure structure = projectStructureService.generateAndSaveStructure(path, id);
        return ResponseEntity.ok(structure);
    }

    @GetMapping("/{id}/structure")
    public ResponseEntity<String> getStructure(@PathVariable Long id) {
        return projectStructureRepository.findByRepositoryId(id)
                .map(structure -> ResponseEntity.ok().body(structure.getStructureJson()))
                .orElse(ResponseEntity.notFound().build());
    }
}
