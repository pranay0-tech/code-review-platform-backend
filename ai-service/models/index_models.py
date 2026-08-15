from pydantic import BaseModel


class CodeChunk(BaseModel):
    chunkId: str
    className: str
    content: str
    file: str | None = None
    package: str | None = None
    method: str | None = None


class IndexRepositoryRequest(BaseModel):
    chunks: list[CodeChunk]