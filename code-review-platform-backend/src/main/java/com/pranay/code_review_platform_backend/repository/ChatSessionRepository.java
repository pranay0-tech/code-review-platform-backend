package com.pranay.code_review_platform_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSession, Long> {

    // Fetch all chat sessions for a specific user
    List<ChatSession> findByUserId(Long userId);

    // Fetch all chat sessions for a specific user and repository combination
    List<ChatSession> findByUserIdAndRepositoryIdOrderByCreatedAtDesc(Long userId, Long repositoryId);

    // Fetch an active session by user ID and session ID to enforce data security
    Optional<ChatSession> findByIdAndUserId(Long id, Long userId);
}