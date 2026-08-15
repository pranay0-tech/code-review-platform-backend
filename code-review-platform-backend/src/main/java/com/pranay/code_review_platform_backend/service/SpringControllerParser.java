package com.pranay.code_review_platform_backend.service;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.pranay.code_review_platform_backend.parser.dto.EndpointMetadata;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SpringControllerParser {

    // Maps Spring mapping annotations to HTTP methods
    private static final Map<String, String> HTTP_METHOD_MAP = Map.of(
            "GetMapping", "GET",
            "PostMapping", "POST",
            "PutMapping", "PUT",
            "DeleteMapping", "DELETE",
            "PatchMapping", "PATCH",
            "RequestMapping", "ALL"
    );

    /**
     * Parses a Java source file and extracts all REST endpoints.
     */
    public List<EndpointMetadata> parseControllerFile(Path filePath) throws Exception {
        CompilationUnit cu = StaticJavaParser.parse(filePath);
        List<EndpointMetadata> endpoints = new ArrayList<>();
        cu.accept(new ControllerVisitor(), endpoints);
        return endpoints;
    }

    private static class ControllerVisitor extends VoidVisitorAdapter<List<EndpointMetadata>> {

        @Override
        public void visit(ClassOrInterfaceDeclaration n, List<EndpointMetadata> collector) {
            super.visit(n, collector);

            // 1. Check if class is annotated with @RestController or @Controller
            boolean isController = n.getAnnotations().stream()
                    .anyMatch(a -> a.getNameAsString().equals("RestController") || 
                                   a.getNameAsString().equals("Controller"));

            if (!isController) {
                return;
            }

            String controllerName = n.getNameAsString();

            // 2. Extract Base Path from Class-level @RequestMapping
            String basePath = n.getAnnotationByName("RequestMapping")
                    .flatMap(ControllerVisitor::extractPathFromAnnotation)
                    .orElse("");

            // 3. Iterate over methods to extract individual endpoints
            for (MethodDeclaration method : n.getMethods()) {
                for (AnnotationExpr annotation : method.getAnnotations()) {
                    String annotationName = annotation.getNameAsString();

                    if (HTTP_METHOD_MAP.containsKey(annotationName)) {
                        String httpMethod = HTTP_METHOD_MAP.get(annotationName);
                        String subPath = extractPathFromAnnotation(annotation).orElse("");

                        // Special handling for @RequestMapping(method = RequestMethod.X) on methods
                        if ("RequestMapping".equals(annotationName)) {
                            httpMethod = extractHttpMethodFromRequestMapping(annotation).orElse("GET");
                        }

                        // Combine Base Path + Sub Path (e.g., /api/auth + /login -> /api/auth/login)
                        String fullPath = combinePaths(basePath, subPath);

                        collector.add(EndpointMetadata.builder()
                                .controllerName(controllerName)
                                .httpMethod(httpMethod)
                                .fullPath(fullPath)
                                .methodName(method.getNameAsString())
                                .build());
                    }
                }
            }
        }

        // Extracts path string from annotation attributes: @PostMapping("/login") or @PostMapping(value = "/login") or @PostMapping(path = "/login")
        private static Optional<String> extractPathFromAnnotation(AnnotationExpr annotation) {
            if (annotation.isSingleMemberAnnotationExpr()) {
                return Optional.of(cleanPath(annotation.asSingleMemberAnnotationExpr().getMemberValue().toString()));
            }

            if (annotation.isNormalAnnotationExpr()) {
                for (MemberValuePair pair : annotation.asNormalAnnotationExpr().getPairs()) {
                    String key = pair.getNameAsString();
                    if ("value".equals(key) || "path".equals(key)) {
                        return Optional.of(cleanPath(pair.getValue().toString()));
                    }
                }
            }
            return Optional.empty();
        }

        private static Optional<String> extractHttpMethodFromRequestMapping(AnnotationExpr annotation) {
            if (annotation.isNormalAnnotationExpr()) {
                for (MemberValuePair pair : annotation.asNormalAnnotationExpr().getPairs()) {
                    if ("method".equals(pair.getNameAsString())) {
                        String val = pair.getValue().toString();
                        if (val.contains("POST")) return Optional.of("POST");
                        if (val.contains("GET")) return Optional.of("GET");
                        if (val.contains("PUT")) return Optional.of("PUT");
                        if (val.contains("DELETE")) return Optional.of("DELETE");
                        if (val.contains("PATCH")) return Optional.of("PATCH");
                    }
                }
            }
            return Optional.empty();
        }

        // Clean quotes and formatting from raw string literals (e.g. "\" /login \"" -> "/login")
        private static String cleanPath(String raw) {
            String cleaned = raw.replace("\"", "").trim();
            if (!cleaned.startsWith("/")) {
                cleaned = "/" + cleaned;
            }
            return cleaned;
        }

        // Combines base path and method subpath cleanly without double slashes (e.g., /api/auth/ + /login -> /api/auth/login)
        private static String combinePaths(String base, String sub) {
            String b = base.endsWith("/") ? base.substring(0, base.length() - 1) : base;
            String s = sub.startsWith("/") ? sub : "/" + sub;

            if (b.isEmpty()) return s;
            if (s.equals("/")) return b;

            return b + s;
        }
    }
}