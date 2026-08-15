import os

import httpx
from dotenv import load_dotenv


load_dotenv()


class LLMService:

    def __init__(self):
        self.base_url = os.getenv(
            "OLLAMA_BASE_URL",
            "http://localhost:11434"
        )

        self.model = os.getenv(
            "OLLAMA_MODEL"
        )

        if not self.model:
            raise ValueError(
                "OLLAMA_MODEL environment variable is not configured"
            )

    def generate(
        self,
        question: str,
        context: str
    ) -> str:

        prompt = f"""
You are a software architect.

Answer only using the provided code context.

If the information is unavailable in the provided context,
say that the information is unavailable.

Code context:
{context}

Question:
{question}
"""

        response = httpx.post(
            f"{self.base_url}/api/generate",
            json={
                "model": self.model,
                "prompt": prompt,
                "stream": False
            },
            timeout=120.0
        )

        response.raise_for_status()

        data = response.json()

        return data["response"]