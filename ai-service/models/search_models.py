from pydantic import BaseModel


class SearchRequest(BaseModel):
    repositoryId: int
    query: str


class ChatRequest(BaseModel):
    repositoryId: int
    question: str


class ChatResponse(BaseModel):
    answer: str
    sources: list[str] = []