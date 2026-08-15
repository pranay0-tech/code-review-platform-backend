from fastapi import APIRouter

from models.index_models import IndexRepositoryRequest
from services.repository_indexer import RepositoryIndexer


router = APIRouter()

indexer = RepositoryIndexer()


@router.post("/index-repository/{repository_id}")
def index_repository(
    repository_id: int,
    request: IndexRepositoryRequest
):
    chunks = []

    for chunk in request.chunks:
        chunks.append({
            "chunkId": chunk.chunkId,
            "class": chunk.className,
            "content": chunk.content,
            "file": chunk.file,
            "package": chunk.package,
            "method": chunk.method
        })

    return indexer.index_repository(
        repository_id=repository_id,
        chunks=chunks
    )