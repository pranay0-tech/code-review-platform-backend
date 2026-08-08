package com.pranay.code_review_platform_backend.repository;

import com.pranay.code_review_platform_backend.entity.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryRepository extends JpaRepository<Repository, Long> {
}
