package com.pranay.code_review_platform_backend.repository;

import com.pranay.code_review_platform_backend.parser.model.ParsingJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParsingJobRepository extends JpaRepository<ParsingJob, Long> {

    Optional<ParsingJob> findTopByRepositoryIdOrderByCreatedAtDesc(Long repositoryId);
}