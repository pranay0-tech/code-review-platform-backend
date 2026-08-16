package main.java.com.pranay.code_review_platform_backend.review.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "review_issues")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long repositoryId;

    @Enumerated(EnumType.STRING)
    private Severity severity;

    @Enumerated(EnumType.STRING)
    private IssueType type;

    private String fileName;

    private String className;

    private String methodName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String suggestion;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum Severity {
        LOW,
        MEDIUM,
        HIGH,
        CRITICAL
    }

    public enum IssueType {
        CODE_SMELL,
        DUPLICATE_CODE,
        COMPLEXITY,
        STYLE,
        BUG_RISK,
        LONG_METHOD,
        GOD_CLASS,
        DEEP_NESTING,
          HIGH_COMPLEXITY,
          UNUSED_IMPORT,
          EMPTY_CATCH
          

    }
}