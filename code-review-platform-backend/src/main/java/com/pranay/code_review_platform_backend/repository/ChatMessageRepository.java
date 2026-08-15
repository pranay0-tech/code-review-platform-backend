package com.pranay.code_review_platform_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // Fetches the 5 most recent messages (newest first) for LLM context window limits
    List<ChatMessage> findTop5BySessionIdOrderByCreatedAtDesc(Long sessionId);

    // Fetches full chat history ordered chronologically for rendering in the Frontend UI
    List<ChatMessage> findBySessionIdOrderByCreatedAtAsc(Long sessionId);
}