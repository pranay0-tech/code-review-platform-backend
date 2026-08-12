package com.pranay.code_review_platform_backend.repository;

import com.pranay.code_review_platform_backend.parser.model.ProjectStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectStructureRepository extends JpaRepository<ProjectStructure, Long> {

    Optional<ProjectStructure> findByRepositoryId(Long repositoryId);
}