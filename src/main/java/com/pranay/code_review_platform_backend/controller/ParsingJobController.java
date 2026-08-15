package com.pranay.code_review_platform_backend.controller;

import com.pranay.code_review_platform_backend.parser.dto.ParsingJobResponse;
import com.pranay.code_review_platform_backend.parser.service.ParsingJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parsing-jobs")
@RequiredArgsConstructor
public class ParsingJobController {

    private final ParsingJobService parsingJobService;

    @GetMapping("/{jobId}")
    public ResponseEntity<ParsingJobResponse> getJobStatus(
            @PathVariable Long jobId) {

        return ResponseEntity.ok(
                parsingJobService.getJobStatus(jobId)
        );
    }
}