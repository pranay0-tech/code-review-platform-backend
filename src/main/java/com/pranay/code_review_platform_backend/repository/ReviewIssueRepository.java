package main.java.com.pranay.code_review_platform_backend.repository;

import com.pranay.code_review_platform_backend.review.model.ReviewIssue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewIssueRepository extends JpaRepository<ReviewIssue, Long> {

    List<ReviewIssue> findByRepositoryId(Long repositoryId);

    List<ReviewIssue> findByRepositoryIdAndSeverity(
            Long repositoryId,
            ReviewIssue.Severity severity
    );

    List<ReviewIssue> findByRepositoryIdAndType(
            Long repositoryId,
            ReviewIssue.IssueType type
    );
}