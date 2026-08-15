from fastapi import APIRouter

from services.insights_service import InsightsService


router = APIRouter()

insights_service = InsightsService()


@router.get("/repository/{repository_id}/insights")
def get_insights(repository_id: int):

    # Temporary test data.
    # Later this will come from your Spring Boot backend.
    dependencies = [
        {
            "sourceClass": "UserController",
            "targetClass": "UserService"
        },
        {
            "sourceClass": "UserService",
            "targetClass": "UserRepository"
        },
        {
            "sourceClass": "AuthController",
            "targetClass": "AuthService"
        },
        {
            "sourceClass": "AuthService",
            "targetClass": "UserRepository"
        }
    ]

    return insights_service.generate_insights(
        dependencies
    )