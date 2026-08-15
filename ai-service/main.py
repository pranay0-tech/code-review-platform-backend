from fastapi import FastAPI

from app.index_controller import router as index_router
from app.search_controller import router as search_router
from app.chat_controller import router as chat_router
from app.index_controller import router as index_router
from app.insights_controller import router as insights_router


app = FastAPI(title="AI Service")

app.include_router(index_router)
app.include_router(search_router)
app.include_router(index_router)
app.include_router(chat_router)

app.include_router(insights_router)



@app.get("/")
def health_check():
    return {
        "service": "AI Service",
        "status": "running"
    }