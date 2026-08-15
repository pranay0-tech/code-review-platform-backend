from embeddings.embedding_service import EmbeddingService
from vectorstore.chroma_service import ChromaService


class RepositoryIndexer:

    def __init__(self):
        self.embedding_service = EmbeddingService()
        self.chroma_service = ChromaService()

    def index_chunks(self, repository_id: int, chunks: list[dict]):

        for chunk in chunks:

            embedding = self.embedding_service.generate_embedding(
                chunk["content"]
            )

            self.chroma_service.add_chunk(
                chunk_id=chunk["chunkId"],
                repository_id=repository_id,
                class_name=chunk["class"],
                content=chunk["content"],
                embedding=embedding,
                file_path=chunk.get("file"),
                package=chunk.get("package"),
                method=chunk.get("method")
            )

        return {
            "status": "indexed"
        }