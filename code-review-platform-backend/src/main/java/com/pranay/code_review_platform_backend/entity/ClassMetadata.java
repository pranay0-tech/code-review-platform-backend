package com.pranay.code_review_platform_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "class_metadata")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "repository_id", nullable = false)
    private Long repositoryId;

    @Column(name = "class_name", nullable = false)
    private String className;

    @Column(name = "package_name")
    private String packageName;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "class_type")
    private String classType;

    @Column(name = "method_count", nullable = false)
    private int methodCount;

    @Column(name = "annotations", columnDefinition = "TEXT")
    private String annotations;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}