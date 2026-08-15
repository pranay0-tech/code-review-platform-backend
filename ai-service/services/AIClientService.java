

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIClientService {

    private final WebClient.Builder webClientBuilder;

    public List<Map<String, Object>> search(
            Long repositoryId,
            String query
    ) {

        Map<String, Object> request = Map.of(
                "repositoryId", repositoryId,
                "query", query
        );

        return webClientBuilder
                .baseUrl("http://localhost:8001")
                .build()
                .post()
                .uri("/search")
                .bodyValue(request)
                .retrieve()
                .bodyToFlux(Map.class)
                .collectList()
                .block();
    }
}