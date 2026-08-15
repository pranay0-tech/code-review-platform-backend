from fastapi import APIRouter
from models.search_models import ChatRequest, ChatResponse
from embeddings.embedding_service import EmbeddingService
from vectorstore.chroma_service import ChromaService
from services.llm_service import LLMService
from models.chat_models import ChatRequest
from services.rag_service import RAGService

from models.chat_models import ChatRequest


router = APIRouter()

embedding_service = EmbeddingService()
chroma_service = ChromaService()
llm_service = LLMService()
rag_service = RAGService()


@router.post("/chat")
def chat(request: ChatRequest):

    return rag_service.answer(
        repository_id=request.repositoryId,
        question=request.question
    )


@router.post("/chat", response_model=ChatResponse)
def chat_with_repository(request: ChatRequest):
    # 1. Embed the user's question
    question_embedding = embedding_service.generate_embedding(request.question)

    # 2. Retrieve top 5 relevant chunks from ChromaDB
    search_results = chroma_service.search(
        embedding=question_embedding,
        repository_id=request.repositoryId,
        limit=5,
    )

    # 3. Format context chunks and collect source file names
    context_chunks = []
    sources = set()

    documents = search_results.get("documents", [[]])[0]
    metadatas = search_results.get("metadatas", [[]])[0]

    for doc, meta in zip(documents, metadatas):
        context_chunks.append(
            {
                "file": meta.get("file", "Unknown"),
                "method": meta.get("method", "Unknown"),
                "content": doc,
            }
        )
        if meta.get("file"):
            sources.add(meta["file"])

    # 4. Generate answer via Ollama
    answer = llm_service.generate_answer(
        question=request.question, context_chunks=context_chunks
    )

    # 5. Return answer with source files cited
    return ChatResponse(answer=answer, sources=list(sources))