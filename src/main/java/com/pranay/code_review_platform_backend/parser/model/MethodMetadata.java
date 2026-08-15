package com.pranay.code_review_platform_backend.parser.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "method_metadata")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MethodMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long repositoryId;

    private String methodName;

    private String returnType;

    private int parameterCount;

    private String className;

    private String accessModifier;
}