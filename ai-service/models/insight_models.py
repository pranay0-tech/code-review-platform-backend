from pydantic import BaseModel


class RepositoryInsightsResponse(BaseModel):
    topClasses: list[str]
    mostReferencedClass: str | None