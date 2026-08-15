class InsightsService:

    def generate_insights(
        self,
        class_dependencies: list[dict]
    ) -> dict:

        reference_count: dict[str, int] = {}

        for dependency in class_dependencies:

            target_class = dependency["targetClass"]

            reference_count[target_class] = (
                reference_count.get(target_class, 0) + 1
            )

        sorted_classes = sorted(
            reference_count.items(),
            key=lambda item: item[1],
            reverse=True
        )

        top_classes = [
            class_name
            for class_name, _ in sorted_classes[:5]
        ]

        most_referenced_class = (
            sorted_classes[0][0]
            if sorted_classes
            else None
        )

        return {
            "topClasses": top_classes,
            "mostReferencedClass": most_referenced_class
        }