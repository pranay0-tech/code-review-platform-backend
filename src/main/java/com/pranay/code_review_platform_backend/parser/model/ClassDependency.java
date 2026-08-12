package com.pranay.code_review_platform_backend.parser.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "class_dependencies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassDependency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_class", nullable = false)
    private String sourceClass;

    @Column(name = "target_class", nullable = false)
    private String targetClass;
}