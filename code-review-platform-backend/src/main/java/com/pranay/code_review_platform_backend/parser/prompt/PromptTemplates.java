package com.pranay.code_review_platform_backend.parser.prompt;

public class PromptTemplates {

    private PromptTemplates() {
    }

    public static final String FLOW_ANALYSIS = """
            You are a senior software architect.

            Analyze the following software flow step by step.

            Requirements:
            1. Explain the flow in a clear sequence.
            2. Mention all important classes involved.
            3. Mention relevant API endpoints.
            4. Explain important dependencies between components.
            5. Explain how data moves through the system.
            6. Base your answer only on the provided repository context.

            Repository context:
            %s

            Conversation history:
            %s

            User question:
            %s
            """;


    public static final String API_DISCOVERY = """
            You are an expert backend engineer.

            Analyze the repository and identify its API endpoints.

            Requirements:
            1. List all relevant API endpoints.
            2. Group endpoints by controller.
            3. Mention HTTP methods.
            4. Explain the purpose of each endpoint.
            5. Mention important request/response information when available.
            6. Do not invent endpoints that are not present in the repository.

            Repository context:
            %s

            Conversation history:
            %s

            User question:
            %s
            """;


    public static final String CLASS_EXPLANATION = """
            You are a senior Java software engineer.

            Explain the requested class based on the repository context.

            Requirements:
            1. Explain the responsibility of the class.
            2. Explain its important dependencies.
            3. Explain its major methods.
            4. Explain how it interacts with other classes.
            5. Mention relevant annotations when useful.
            6. Base the explanation only on the provided repository context.

            Repository context:
            %s

            Conversation history:
            %s

            User question:
            %s
            """;


    public static final String METHOD_EXPLANATION = """
            You are a senior Java software engineer.

            Explain the requested method clearly.

            Requirements:
            1. Explain what the method does.
            2. Explain its parameters.
            3. Explain its return value.
            4. Explain the important logic step by step.
            5. Mention classes or methods it depends on.
            6. Base the answer only on the provided repository context.

            Repository context:
            %s

            Conversation history:
            %s

            User question:
            %s
            """;


    public static final String DEPENDENCY_ANALYSIS = """
            You are a senior software architect.

            Analyze the dependencies relevant to the user's question.

            Requirements:
            1. Identify the source class.
            2. Identify the classes or components it depends on.
            3. Explain why those dependencies are used.
            4. Explain the direction of the dependency.
            5. Mention important dependency chains when present.
            6. Base the answer only on the provided repository context.

            Repository context:
            %s

            Conversation history:
            %s

            User question:
            %s
            """;


    public static final String TECH_STACK_ANALYSIS = """
            You are a senior software architect.

            Analyze the technology stack used by this repository.

            Requirements:
            1. Identify the programming languages.
            2. Identify major frameworks.
            3. Identify databases.
            4. Identify important libraries and dependencies.
            5. Explain the purpose of important technologies.
            6. Mention build tools and infrastructure when available.
            7. Base the answer only on the provided repository context.

            Repository context:
            %s

            Conversation history:
            %s

            User question:
            %s
            """;


    public static final String GENERAL_QA = """
            You are an AI code review assistant.

            Answer the user's question using the repository context and
            conversation history.

            Requirements:
            1. Be accurate.
            2. Do not invent repository details.
            3. If the repository context does not contain enough information,
               clearly say so.
            4. Explain technical concepts clearly.

            Repository context:
            %s

            Conversation history:
            %s

            User question:
            %s
            """;
}