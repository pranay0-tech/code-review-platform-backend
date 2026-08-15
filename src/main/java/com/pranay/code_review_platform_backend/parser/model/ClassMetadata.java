package com.pranay.code_review_platform_backend.parser.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "class_metadata")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long repositoryId;

    private String className;

    private String packageName;

    private String filePath;

    private boolean isInterface;

    private boolean isAbstract;
}
