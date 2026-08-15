package com.pranay.code_review_platform_backend.parser.service;

import com.pranay.code_review_platform_backend.entity.ClassMetadata;
import com.pranay.code_review_platform_backend.parser.dto.OnboardingGuideResponse;
import com.pranay.code_review_platform_backend.parser.dto.OnboardingStepDto;
import com.pranay.code_review_platform_backend.repository.ClassMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OnboardingEngineService {

    private final ClassMetadataRepository classMetadataRepository;

    @Transactional(readOnly = true)
    public OnboardingGuideResponse generateOnboardingGuide(Long repositoryId) {
        List<ClassMetadata> allMetadata = classMetadataRepository.findByRepositoryId(repositoryId);
        Map<String, ClassMetadata> classMap = allMetadata.stream()
                .collect(Collectors.toMap(ClassMetadata::getClassName, c -> c, (existing, replacement) -> existing));

        List<OnboardingStepDto> orderedFlow = new ArrayList<>();
        Set<String> visitedClasses = new HashSet<>();
        AtomicInteger stepCounter = new AtomicInteger(1);

        // 1. Find Entry Points (Controllers)
        List<ClassMetadata> controllers = allMetadata.stream()
                .filter(c -> "CONTROLLER".equalsIgnoreCase(c.getClassType()))
                .sorted(Comparator.comparingInt(ClassMetadata::getMethodCount).reversed())
                .toList();

        for (ClassMetadata controller : controllers) {
            if (visitedClasses.contains(controller.getClassName())) continue;

            // Step A: Add Controller
            visitedClasses.add(controller.getClassName());
            List<String> controllerDeps = extractDependencies(controller, classMap);
            orderedFlow.add(createStep(stepCounter.getAndIncrement(), controller, "Entry Point: Handles incoming API requests.", controllerDeps));

            // Step B: Follow Dependencies to Services & Repositories
            for (String depName : controllerDeps) {
                ClassMetadata depMeta = classMap.get(depName);
                if (depMeta != null && !visitedClasses.contains(depName)) {
                    visitedClasses.add(depName);
                    List<String> subDeps = extractDependencies(depMeta, classMap);
                    
                    String rationale = "SERVICE".equalsIgnoreCase(depMeta.getClassType()) 
                            ? "Business Logic: Processes core domain operations called by " + controller.getClassName()
                            : "Data Access Layer: Manages persistence logic.";

                    orderedFlow.add(createStep(stepCounter.getAndIncrement(), depMeta, rationale, subDeps));

                    // Step C: Follow Service -> Repository / Entity
                    for (String childDepName : subDeps) {
                        ClassMetadata childMeta = classMap.get(childDepName);
                        if (childMeta != null && !visitedClasses.contains(childDepName)) {
                            visitedClasses.add(childDepName);
                            orderedFlow.add(createStep(
                                    stepCounter.getAndIncrement(), 
                                    childMeta, 
                                    "Data Layer: Referenced by " + depMeta.getClassName(), 
                                    extractDependencies(childMeta, classMap)
                            ));
                        }
                    }
                }
            }
        }

        // 2. Identify Security & Configuration
        List<ClassMetadata> securityAndConfig = allMetadata.stream()
                .filter(c -> isInfrastructure(c) && !visitedClasses.contains(c.getClassName()))
                .toList();

        for (ClassMetadata config : securityAndConfig) {
            visitedClasses.add(config.getClassName());
            orderedFlow.add(createStep(
                    stepCounter.getAndIncrement(), 
                    config, 
                    "Infrastructure & Security: Configures global authentication, filters, or beans.", 
                    extractDependencies(config, classMap)
            ));
        }

        // 3. Identify External Integrations
        List<ClassMetadata> integrations = allMetadata.stream()
                .filter(c -> isExternalIntegration(c) && !visitedClasses.contains(c.getClassName()))
                .toList();

        for (ClassMetadata integration : integrations) {
            visitedClasses.add(integration.getClassName());
            orderedFlow.add(createStep(
                    stepCounter.getAndIncrement(), 
                    integration, 
                    "External Integration: Connects to third-party APIs or message queues.", 
                    extractDependencies(integration, classMap)
            ));
        }

        // 4. Fallback: Add remaining unvisited classes sorted by method count
        allMetadata.stream()
                .filter(c -> !visitedClasses.contains(c.getClassName()))
                .sorted(Comparator.comparingInt(ClassMetadata::getMethodCount).reversed())
                .forEach(remaining -> {
                    visitedClasses.add(remaining.getClassName());
                    orderedFlow.add(createStep(
                            stepCounter.getAndIncrement(), 
                            remaining, 
                            "Supporting Utility/Domain Class.", 
                            extractDependencies(remaining, classMap)
                    ));
                });

        return OnboardingGuideResponse.builder()
                .repositoryId(repositoryId)
                .prioritizedFlow(orderedFlow)
                .build();
    }

    private OnboardingStepDto createStep(int order, ClassMetadata metadata, String rationale, List<String> dependencies) {
        return OnboardingStepDto.builder()
                .stepOrder(order)
                .className(metadata.getClassName())
                .classType(metadata.getClassType() != null ? metadata.getClassType() : "OTHER")
                .packageName(metadata.getPackageName())
                .rationale(rationale)
                .dependencies(dependencies)
                .build();
    }

    private List<String> extractDependencies(ClassMetadata metadata, Map<String, ClassMetadata> classMap) {
        if (metadata.getAnnotations() == null || metadata.getAnnotations().isBlank()) {
            return Collections.emptyList();
        }

        return classMap.keySet().stream()
                .filter(className -> !className.equals(metadata.getClassName()) && 
                                     metadata.getAnnotations().contains(className))
                .toList();
    }

    private boolean isInfrastructure(ClassMetadata metadata) {
        String name = metadata.getClassName() != null ? metadata.getClassName().toLowerCase() : "";
        String pkg = metadata.getPackageName() != null ? metadata.getPackageName().toLowerCase() : "";
        return name.contains("config") || name.contains("filter") || name.contains("security") ||
               pkg.contains("config") || pkg.contains("security");
    }

    private boolean isExternalIntegration(ClassMetadata metadata) {
        String name = metadata.getClassName() != null ? metadata.getClassName().toLowerCase() : "";
        String pkg = metadata.getPackageName() != null ? metadata.getPackageName().toLowerCase() : "";
        return name.contains("client") || name.contains("feign") || name.contains("kafka") || 
               name.contains("rabbit") || name.contains("s3") || pkg.contains("client") || pkg.contains("integration");
    }
}