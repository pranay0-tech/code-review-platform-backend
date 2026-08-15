from embeddings.embedding_service import EmbeddingService
from vectorstore.chroma_service import ChromaService
from services.llm_service import LLMService


class RAGService:

    def __init__(self):
        self.embedding_service = EmbeddingService()
        self.chroma_service = ChromaService()
        self.llm_service = LLMService()

    def answer(
        self,
        repository_id: int,
        question: str
    ) -> dict:

        # 1. Convert question into an embedding
        query_embedding = self.embedding_service.generate_embedding(
            question
        )

        # 2. Retrieve top code chunks
        results = self.chroma_service.search(
            embedding=query_embedding,
            repository_id=repository_id,
            limit=5
        )

        documents = results.get("documents", [[]])[0]
        metadatas = results.get("metadatas", [[]])[0]

        # Handle empty search results
        if not documents:
            return {
                "answer": "I could not find enough relevant code in this repository to answer the question.",
                "sources": []
            }

        # 3. Build code context & extract sources
        context_parts = []
        sources = []

        for i in range(len(documents)):
            metadata = metadatas[i] or {}
            file_path = metadata.get("file", "Unknown File")
            class_name = metadata.get("class", "Unknown Class")
            method_name = metadata.get("method", "Unknown Method")

            chunk_str = (
                f"File: {file_path}\n"
                f"Class: {class_name}\n"
                f"Method: {method_name}\n\n"
                f"Code:\n{documents[i]}"
            )
            context_parts.append(chunk_str.strip())

            # Collect unique file sources
            if file_path and file_path not in sources:
                sources.append(file_path)

        context = "\n\n---\n\n".join(context_parts)

        # 4. Generate LLM answer
        answer = self.llm_service.generate(
            question=question,
            context=context
        )

        return {
            "answer": answer,
            "sources": sources
        }