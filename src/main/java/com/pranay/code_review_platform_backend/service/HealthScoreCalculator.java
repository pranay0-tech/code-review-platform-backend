package main.java.com.pranay.code_review_platform_backend.service;

import com.pranay.code_review_platform_backend.review.model.ReviewIssue;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthScoreCalculator {

    public int calculate(List<ReviewIssue> issues) {

        int score = 100;

        int complexityPenalty = 0;
        int duplicationPenalty = 0;
        int codeSmellPenalty = 0;
        int largeClassPenalty = 0;

        for (ReviewIssue issue : issues) {

            switch (issue.getType()) {

                case HIGH_COMPLEXITY:
                    complexityPenalty += 5;
                    break;

                case DUPLICATE_CODE:
                    duplicationPenalty += 4;
                    break;

                case LONG_METHOD:
                case DEEP_NESTING:
                case EMPTY_CATCH:
                case UNUSED_IMPORT:
                case BAD_NAMING:
                case MAGIC_NUMBER:
                    codeSmellPenalty += 2;
                    break;

                case GOD_CLASS:
                    largeClassPenalty += 6;
                    break;

                default:
                    break;
            }
        }

        score -= complexityPenalty;
        score -= duplicationPenalty;
        score -= codeSmellPenalty;
        score -= largeClassPenalty;

        return Math.max(score, 0);
    }
}