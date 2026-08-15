import chromadb


class ChromaService:

    def __init__(self):
        self.client = chromadb.PersistentClient(
            path="./chroma_data"
        )

        self.collection = self.client.get_or_create_collection(
            name="repository_chunks"
        )

    def add_chunk(
        self,
        chunk_id: str,
        repository_id: int,
        class_name: str,
        content: str,
        embedding: list[float],
        file_path: str | None = None,
        package: str | None = None,
        method: str | None = None
    ):
        metadata = {
            "repositoryId": str(repository_id),
            "class": class_name,
        }

        if package:
            metadata["package"] = package

        if file_path:
            metadata["file"] = file_path

        if method:
            metadata["method"] = method

        self.collection.add(
            ids=[chunk_id],
            embeddings=[embedding],
            documents=[content],
            metadatas=[metadata]
        )

    def search(
        self,
        embedding: list[float],
        repository_id: int,
        limit: int = 5
    ):
        return self.collection.query(
            query_embeddings=[embedding],
            n_results=limit,
            where={
                "repositoryId": str(repository_id)
            }
        )