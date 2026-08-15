from pydantic import BaseModel


class ChatRequest(BaseModel):
    repositoryId: int
    question: str