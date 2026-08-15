package com.pranay.code_review_platform_backend.repository;

import com.pranay.code_review_platform_backend.parser.model.ClassDependency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassDependencyRepository extends JpaRepository<ClassDependency, Long> {

    List<ClassDependency> findBySourceClass(String sourceClass);

    List<ClassDependency> findByTargetClass(String targetClass);

    boolean existsBySourceClassAndTargetClass(String sourceClass, String targetClass);
}