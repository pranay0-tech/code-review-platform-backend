from fastapi import APIRouter
from models.search_models import SearchRequest
from embeddings.embedding_service import EmbeddingService
from vectorstore.chroma_service import ChromaService

router = APIRouter()

embedding_service = EmbeddingService()
chroma_service = ChromaService()


@router.post("/search")
def search(request: SearchRequest):
    query_embedding = embedding_service.generate_embedding(request.query)

    results = chroma_service.search(
        embedding=query_embedding,
        repository_id=request.repositoryId,
        limit=5,
    )

    response = []

    # Safe extraction with fallback to empty list
    ids = results.get("ids", [[]])[0]
    documents = results.get("documents", [[]])[0]
    metadatas = results.get("metadatas", [[]])[0]
    distances = results.get("distances", [[]])[0]

    for i in range(len(ids)):
        response.append(
            {
                "chunkId": ids[i],
                "class": metadatas[i].get("class"),
                "file": metadatas[i].get("file"),
                "method": metadatas[i].get("method"),
                "content": documents[i],
                "distance": distances[i] if i < len(distances) else None,
            }
        )

    return response